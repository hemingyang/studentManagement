package com.zcx.studentManagement.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zcx.studentManagement.dao.AdminDao;
import com.zcx.studentManagement.entity.BaseResponse;
import com.zcx.studentManagement.utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/addUser")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("1111111111111");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String jsonRegistration = RequestUtil.getRequestBody(req);
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonParser().parse(jsonRegistration).getAsJsonObject();

        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        resp.setContentType("application/json");
        BaseResponse<Integer> baseResponse = new BaseResponse<Integer>();

        boolean registrationSuccess = AdminDao.insertAdministrator(username, password);

        if (registrationSuccess) {
            baseResponse.setCode(200);
            baseResponse.setMsg("注册成功");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            baseResponse.setCode(500);
            baseResponse.setMsg("注册失败，请重试");
        }

        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(baseResponse));
        out.flush();
        out.close();
    }
}
