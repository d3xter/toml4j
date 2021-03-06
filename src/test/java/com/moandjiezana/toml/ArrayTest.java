package com.moandjiezana.toml;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.moandjiezana.toml.testutils.Utils;

public class ArrayTest {
  
  @Test
  public void should_get_array() throws Exception {
    Toml toml = new Toml().parse("list = [\"a\", \"b\", \"c\"]");

    assertEquals(asList("a", "b", "c"), toml.getList("list", String.class));
  }

  @Test
  public void should_allow_multiline_array() throws Exception {
    Toml toml = new Toml().parse(file("should_allow_multiline_array"));

    assertEquals(asList("a", "b", "c"), toml.getList("a", String.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void should_get_nested_arrays() throws Exception {
    Toml clients = new Toml().parse("data = [ [\"gamma\", \"delta\"], [1, 2]] # just an update to make sure parsers support it");

    assertEquals(asList(asList("gamma", "delta"), asList(1L, 2L)), clients.getList("data", String.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void should_get_nested_arrays_with_no_space_between_outer_and_inner_array() throws Exception {
    Toml clients = new Toml().parse("data = [[\"gamma\", \"delta\"], [1, 2]] # just an update to make sure parsers support it");

    assertEquals(asList(asList("gamma", "delta"), asList(1L, 2L)), clients.getList("data", String.class));
  }

  @Test
  public void should_return_empty_list_when_no_value_for_table_array() throws Exception {
    List<Toml> tomls = new Toml().parse("[a]").getTables("b");

    assertTrue(tomls.isEmpty());
  }

  @Test
  public void should_ignore_comma_at_end_of_array() throws Exception {
    Toml toml = new Toml().parse("key=[1,2,3,]");

    assertEquals(asList(1L, 2L, 3L), toml.getList("key", Long.class));
  }
  
  private File file(String file) {
    return Utils.file(getClass(), file);
  }
}
