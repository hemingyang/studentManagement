package com.zcx.studentManagement.servlet.StudentServlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

@WebServlet(urlPatterns = "/editStudent")
public class StudentEditServlet extends HttpServlet {   // 更新学生信息的Servlet
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        req.setCharacterEncoding("utf-8");

        // 获取请求体中的JSON数据
        String jsonStudent = RequestUtil.getRequestBody(req);

        // 使用Gson解析JSON数据
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonParser().parse(jsonStudent).getAsJsonObject();

        // 提取学生信息
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

        // 根据学生ID获取学生对象
        Student student = StudentDao.getStudentById(id);

        // 如果学生对象不为空，更新学生信息
        if(student != null){
            student.setId(id);
            student.setName(name);
            student.setSex(sex);
            student.setBirthday(birthday);
            student.setMobile(mobile);
            student.setEmail(email);
            student.setClazzName(clazzName);
            student.setTeacherName(teacherName);
        }

        // 调用DAO层更新学生信息
        int rows = StudentDao.updateStudent(student);

        // 设置响应字符编码和内容类型
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        // 构建响应对象
        BaseResponse<Integer> response = new BaseResponse<Integer>();

        // 根据更新结果设置响应状态和消息
        if(rows > 0){
            response.setCode(200);
            response.setMsg("编辑成功");
        }else{
            response.setCode(600);
            response.setMsg("编辑失败");
        }

        // 将响应对象转为JSON格式并输出到响应流
        PrintWriter out = resp.getWriter();
        out.print(gson.toJson(response));
        out.flush();
        out.close();
    }
}
