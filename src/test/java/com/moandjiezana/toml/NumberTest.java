package com.moandjiezana.toml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NumberTest {
  @Test
  public void should_get_number() throws Exception {
    Toml toml = new Toml().parse("b = 1001");

    assertEquals(1001, toml.getLong("b").intValue());
  }

  @Test
  public void should_get_negative_number() throws Exception {
    Toml toml = new Toml().parse("b = -1001");

    assertEquals(-1001, toml.getLong("b").intValue());
  }
  
  @Test
  public void should_get_number_with_plus_sign() throws Exception {
    Toml toml = new Toml().parse("a = +1001\nb = 1001");

    assertEquals(toml.getLong("b"), toml.getLong("a"));
  }

  @Test
  public void should_get_double() throws Exception {
    Toml toml = new Toml().parse("double = 5.25");

    assertEquals(5.25D, toml.getDouble("double").doubleValue(), 0.0);
  }

  @Test
  public void should_get_negative_double() throws Exception {
    Toml toml = new Toml().parse("double = -5.25");

    assertEquals(-5.25D, toml.getDouble("double").doubleValue(), 0.0);
  }
  
  @Test
  public void should_get_double_with_a_plus_sign() throws Exception {
    Toml toml = new Toml().parse("double = +5.25");

    assertEquals(5.25D, toml.getDouble("double").doubleValue(), 0.0);
  }
  
  @Test
  public void should_get_exponent() throws Exception {
    Toml toml = new Toml().parse("lower_case = 1e6\nupper_case = 2E6\nwith_plus = 5e+22\nboth_plus = +5E+22\nnegative = -2E-2\nfractional = 6.626e-34");

    assertEquals(Math.pow(1, 6), toml.getDouble("lower_case"), 0.0);
    assertEquals(Math.pow(2, 6), toml.getDouble("upper_case"), 0.0);
    assertEquals(Math.pow(5, 22), toml.getDouble("with_plus"), 0.0);
    assertEquals(Math.pow(5, 22), toml.getDouble("both_plus"), 0.0);
    assertEquals(Math.pow(-2, -2), toml.getDouble("negative"), 0.0);
    assertEquals(Math.pow(6.626D, -34), toml.getDouble("fractional"), 0.0);
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_on_invalid_number() throws Exception {
    new Toml().parse("a = 200-");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_when_illegal_characters_after_float() throws Exception {
    new Toml().parse("number = 3.14  pi");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_when_illegal_characters_after_integer() throws Exception {
    new Toml().parse("number = 314  pi");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_on_float_without_leading_0() {
    new Toml().parse("answer = .12345");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_on_negative_float_without_leading_0() {
    new Toml().parse("answer = -.12345");
  }
  
  @Test(expected = IllegalStateException.class)
  public void should_fail_on_float_without_digits_after_dot() {
    new Toml().parse("answer = 1.");
  }

  @Test(expected = IllegalStateException.class)
  public void should_fail_on_negative_float_without_digits_after_dot() {
    new Toml().parse("answer = -1.");
  }
}
