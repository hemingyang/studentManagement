package com.zcx.studentManagement.servlet.StudentServlet;

import com.google.gson.*;
import com.zcx.studentManagement.dao.StudentDao;
import com.zcx.studentManagement.entity.BaseResponse;
import com.zcx.studentManagement.entity.Student;
import com.zcx.studentManagement.utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/delStudents")
public class StudentsDelServlet extends HttpServlet {   // 批量删除学生信息的Servlet
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 设置请求字符编码
        req.setCharacterEncoding("utf-8");

        // 获取请求体中的JSON数据
        String jsonStudents = RequestUtil.getRequestBody(req);

        // 使用Gson解析JSON数据
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonElements = jsonParser.parse(jsonStudents).getAsJsonArray();

        // 创建学生列表
        List<Student> students = new ArrayList<Student>();

        // 遍历JSON数组，提取学生信息并添加到学生列表
        for (int i = 0; i < jsonElements.size(); i ++) {
            JsonObject jsonObject = jsonElements.get(i).getAsJsonObject();
            String ID = jsonObject.get("id").getAsString();
            int id = Integer.parseInt(ID);
            String name = jsonObject.get("name").getAsString();
            String sex = jsonObject.get("sex").getAsString();
            String mobile = jsonObject.get("mobile").getAsString();
            String email = jsonObject.get("email").getAsString();
            String clazzName = jsonObject.get("clazzName").getAsString();
            String teacherName = jsonObject.get("teacherName").getAsString();
            java.util.Date date = null;

            try {
                date = simpleDateFormat.parse(jsonObject.get("birthday").getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date birthday = new Date(date.getTime());

            // 创建学生对象并设置属性
            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setSex(sex);
            student.setBirthday(birthday);
            student.setMobile(mobile);
            student.setEmail(email);
            student.setClazzName(clazzName);
            student.setTeacherName(teacherName);

            students.add(student);
        }

        // 调用DAO层批量删除学生信息
        int rows = StudentDao.delStudents(students);

        // 设置响应字符编码和内容类型
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        // 构建响应对象
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        // 根据删除结果设置响应状态和消息
        if(rows > 0){
            response.setCode(200);
            response.setMsg("删除成功");
        } else {
            response.setCode(600);
            response.setMsg("无数据删除");
        }

        // 将响应对象转为JSON格式并输出到响应流
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(response));
        out.flush();
        out.close();
    }
}
