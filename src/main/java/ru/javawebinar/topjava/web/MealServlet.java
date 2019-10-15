package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

//    private MealRepository repository;

    private ConfigurableApplicationContext appCtx;
    private MealRestController mealController;
    private ProfileRestController userController;
    private User user;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealController = appCtx.getBean(MealRestController.class);
        userController = appCtx.getBean(ProfileRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action != null && action.equals("sorted")) {
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");


            List<MealTo> list;
            if (!startDate.isEmpty() && !endDate.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
                LocalDateTime first = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
                LocalDateTime end = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.parse(endTime));
                list = mealController.sortBy(first, end, user.getId());
                request.setAttribute("meals", list);
            } else if (!startTime.isEmpty() && !endTime.isEmpty()) {
                list = mealController.sortBy(LocalTime.parse(startTime), LocalTime.parse(endTime), user.getId());
                request.setAttribute("meals", list);
            } else if (!startDate.isEmpty() && !endDate.isEmpty()) {
                list = mealController.sortBy(LocalDate.parse(startDate), LocalDate.parse(endDate), user.getId());
                request.setAttribute("meals", list);
            } else {
                response.sendRedirect("meals");
                return;
            }
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else {
            String id = request.getParameter("id");
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")), user.getId());

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if (meal.isNew()) {
                mealController.create(meal);
            } else {
                mealController.update(meal, user.getId());
            }
            response.sendRedirect("meals");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        user = userController.get();

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealController.delete(id, user.getId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, user.getId()) :
                        mealController.get(getId(request), user.getId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals", user.getRoles().contains(Role.ROLE_ADMIN) ? mealController.getAll() : mealController.getAllById(user.getId()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
