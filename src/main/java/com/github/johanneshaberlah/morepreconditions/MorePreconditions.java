/*
 * Copyright (C) 2019 Johannes Haberlah. All rights reserved-
 * Use of this source code is governed by a MIT-style.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.johanneshaberlah.morepreconditions;

import com.sun.istack.internal.Nullable;

import java.util.function.Predicate;

/**
 * Static convenience methods that help a method or constructor check whether parameters are valid
 * or not. The Guava Preconditions class already contains some functions, but some functions are
 * missing, such as checking several parameters at the same time. If you want to do a NotNull check
 * for n-parameters, you would have to do n-preconditions methods, which is unnecessary code.
 *
 * <pre>
 * @<code>
 *  protected Integer shift(int value, int bitmask){
 *    Preconditions.checkArgument(value > 0);
 *    Preconditions.checkArgument(bitmask > 0);
 *    return value & bitmask;
 *  } // Here we need two methods to check two parameters exactly identically.
 *
 *  protected Integer shift(int value, int bitmask){
 *    MorePreconditions.checkArguments(integer -> integer > 0, value, bitmask);
 *    return value & bitmask;
 *  } // Here we just need one method to check the two parameters
 * </code>
 * </pre>
 *
 * Another example. This time with objects.
 *
 * <pre>
 * @<code>
 *  protected boolean equals(Object obj1, Object obj2){
 *    Preconditions.checkNotNull(obj1);
 *    Preconditions.checkNotNull(obj2);
 *    return obj1.equals(obj2);
 *  } // Again. We need to expressions to check whether one of the arguments is null.
 *
 *  protected boolean equals(Object obj1, Object obj2){
 *    MorePreconditions.checkNotNull(obj1, obj2);
 *    return obj1.equals(obj2);
 *  } // Here we need just one expression.
 * </code>
 * </pre>
 *
 * Next example. We would like to check an argument. The argument should just contain letters from A to Z.
 *
 * <pre>
 * <@code>
 *  protected void test(String string){
 *    MorePreconditions.checkString(string, "//([A-Z])\\w+/g", "The string should just contain letters from A to Z!");
 *    System.out.println(string);
 *  } // When the string contains numbers a IllegalArgumentException will be thrown.
 * </@code>
 * </pre>
 */
public final class MorePreconditions {

  private MorePreconditions() {}

  /**
   * Ensures that an argument is not null.
   *
   * @param reference The argument
   * @param message The message which should be displayed when the argument is null
   * @throws NullPointerException
   * @return reference
   */
  public static <T> T checkNotNull(T reference, @Nullable String message) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(message));
    } else {
      return reference;
    }
  }

  /**
   * Ensures that the supplied arguments are not null.
   *
   * @param references The arguments
   * @throws NullPointerException
   * @return references
   */
  public static Object[] checkNotNull(Object... references) {
    for (Object reference : references) {
      checkNotNull(reference);
    }
    return references;
  }

  /**
   * Validate an argument using Predicates
   *
   * @param predicate The predicate used to test the argument
   * @param t The argument to test
   * @param message The message which should be displayed when the test returns false
   * @return t
   */
  public static <T> T checkArgument(Predicate<T> predicate, T t, @Nullable String message) {
    if (!(predicate.test(t))) {
      throw new IllegalArgumentException(String.valueOf(message));
    }
    return t;
  }

  /**
   * Validate arguments using Predicates
   *
   * @param predicate The predicate used to test the argument
   * @param objects The arguments to test
   * @return objects
   */
  public static Object[] checkArguments(Predicate<Object> predicate, Object... objects) {
    for (Object obj : objects) {
      if (predicate.test(obj)) {
        continue;
      }
      throw new IllegalArgumentException();
    }
    return objects;
  }

  /**
   * Validate a string using regex
   *
   * @param string The string to be validated
   * @param regex The regex to match the string
   * @param message The message to be displayed when the string doesnt match the regex
   * @return string
   */
  public static String checkString(String string, String regex, @Nullable String message) {
    if (!(string.matches(regex))) {
      throw new IllegalArgumentException(String.valueOf(message));
    }
    return string;
  }
}
