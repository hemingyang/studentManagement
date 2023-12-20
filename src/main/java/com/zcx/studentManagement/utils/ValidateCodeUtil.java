package com.zcx.studentManagement.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 */
public class ValidateCodeUtil {

    private int width;
    private int height;
    private int num;
    private String code;
    private static final Random random = new Random();

    /**
     * 单例模式获取对象方法
     */
    private static ValidateCodeUtil validateCodeUtil;

    private ValidateCodeUtil() { // 私有构造方法，单例模式
        code = "01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        num = 5;
    }

    public static ValidateCodeUtil getInstance() {   // 只产生一个实例
        if (validateCodeUtil == null) {
            validateCodeUtil = new ValidateCodeUtil();
        }
        return validateCodeUtil;
    }

    public void set(int width, int height, int num, String code) {
        this.width = width;
        this.height = height;
        this.setNum(num);
        this.setCode(code);
    }

    public void set(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 生成验证码
     *
     * @return 验证码字符串
     */
    public String generateCheckCode() {
        StringBuilder checkCode = new StringBuilder();
        for (int i = 0; i < num; i++) {
            checkCode.append(code.charAt(random.nextInt(code.length())));
        }
        return checkCode.toString();
    }

    /**
     * 生成验证码图片
     *
     * @param checkCode 验证码字符串
     * @return 验证码图片
     */
    public BufferedImage generateCheckImg(String checkCode) {
        // 创建一个图片对象
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphic = img.createGraphics();
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, width, height);
        graphic.setColor(Color.BLACK);
        graphic.drawRect(0, 0, width - 1, height - 1);

        Font font = new Font("微软雅黑", Font.BOLD + Font.ITALIC, (int) (height * 0.8));
        graphic.setFont(font);

        for (int i = 0; i < num; i++) {
            graphic.setColor(new Color(random.nextInt(155), random.nextInt(255), random.nextInt(255)));
            graphic.drawString(String.valueOf(checkCode.charAt(i)), i * (width / num) + 4, (int) (height * 0.8));
        }

        // 加一些点
        for (int i = 0; i < (width + height); i++) {
            graphic.setColor(new Color(random.nextInt(155), random.nextInt(255), random.nextInt(255)));
            graphic.drawOval(random.nextInt(width), random.nextInt(height), 1, 1);
        }

        // 加一些线
        for (int i = 0; i < 5; i++) {
            graphic.setColor(new Color(random.nextInt(155), random.nextInt(255), random.nextInt(255)));
            graphic.drawLine(0, random.nextInt(height), width, random.nextInt(height));
        }

        return img;
    }
}
