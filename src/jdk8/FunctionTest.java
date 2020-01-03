package jdk8;

import com.google.common.collect.Lists;
import org.poc.async.F;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author sunpengwei
 * @description TODO
 * @date 2019/9/17 20:25
 */
public class FunctionTest {


    public static void main(String[] args) throws IOException {
        FunctionTest functionTest = new FunctionTest();

        functionTest.functionTest(n ->{return String.valueOf(n);},Lists.newArrayList(1, 2, 3, 4));
    }


    public void functionTest(Function<Integer,String> apply, List<Integer> nums) throws IOException {
        nums.forEach(n ->{
            String result = apply.apply(n);
            System.out.println(result);
        });



        final Path path = new File( "A:\\1.txt" ).toPath();
        try(Stream< String > lines = Files.lines(path)){
            lines.onClose(()->{ System.out.println("down");}).forEach( System.out::println);
        }catch (Exception e){

        }

    }
}


