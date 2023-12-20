package com.zcx.studentManagement.servlet;

import com.zcx.studentManagement.dao.AdminDao;
import com.zcx.studentManagement.entity.BaseResponse;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/addUser")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        BaseResponse<Integer> baseResponse = new BaseResponse<>();
        boolean registrationSuccess = AdminDao.insertAdministrator(username, password);
        if (registrationSuccess) {
            baseResponse.setCode(200);
            baseResponse.setMsg("注册成功");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            baseResponse.setCode(500);
            baseResponse.setMsg("注册失败，请重试");
        }
        out.print(baseResponse);
        out.flush();
        out.close();
    }
}
