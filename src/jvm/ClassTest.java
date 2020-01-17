package jvm;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2020/1/16 20:04
 */
public class ClassTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoaderMy classLoader = new ClassLoaderMy();
        classLoader.setRoot("D:\\github\\java_common\\target\\classes\\");
        Class clazz = classLoader.findClass("jvm.Test");
        Object obj = clazz.newInstance();
        System.out.println("1:"+clazz.hashCode());
        obj=null;
        System.out.println("2:"+clazz.hashCode());
        classLoader = null;
        System.out.println("3:"+clazz.hashCode());
        clazz = null;

        System.out.println("此时 obj classloader clazz 都为空了");

        classLoader = new ClassLoaderMy();
        classLoader.setRoot("D:\\github\\java_common\\target\\classes\\");
        clazz = classLoader.findClass("jvm.Test");
        System.out.println("4:"+clazz.hashCode());
    }
}



class ClassLoaderMy extends ClassLoader {
    /**
     * 设置类的路径
     */
    private String root;
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }
    private byte[] loadClassData(String className) {
        String fileName = root + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
        InputStream ins = null;
        try {
            ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length = ins.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public void setRoot(String root) {
        this.root = root;
    }


}