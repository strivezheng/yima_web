package io.renren;

import io.renren.common.exception.RRException;
import org.springframework.dao.DuplicateKeyException;

public class ExceptionTest {


    public static void main(String[] args) {

        try {
            throw new RRException("");

        } catch (DuplicateKeyException e) {
            System.out.println("DuplicateKeyException");

        }
//        catch (IllegalArgumentException e) {
//            System.out.println("IllegalArgumentException");
//        } catch (RuntimeException e) {
//            System.out.println("RuntimeException");
//        }
//        catch (Exception e) {
//            System.out.println("Exception");
//        }

    }
}
