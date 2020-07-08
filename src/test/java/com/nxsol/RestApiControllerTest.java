package com.nxsol;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nxsol.controller.RestApiController;
import com.nxsol.model.User;
import com.nxsol.repository.UserRepository;
import com.nxsol.service.UserService;


@RunWith(SpringRunner.class)
@WebMvcTest(value = RestApiController.class, secure = false)
public class RestApiControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService; 
	
	@Mock
	UserRepository userRepository;
	
	String exampleUserJson = "{\"id\":1,\"name\":\"Test\",\"salary\":25000,\"age\":20}";

	User mockUser = new User(1, "Test", 20, 25000);

	@Test
	public void getAll() throws Exception {
		Iterable<User> mockUserItr =  Arrays.asList(mockUser);
		Mockito.when(userService.findAllUsers()).thenReturn(mockUserItr);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get("/api/user/")
											.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "[{id:1,name:Test,age:20,salary:25000}]";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
    }
	
	@Test
	public void getById() throws Exception{
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(mockUser);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
											.get("/api/user/1")
											.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expected = "{id:1,name:Test,age:20,salary:25000}";

		JSONAssert.assertEquals(expected, result.getResponse()
				.getContentAsString(), false);
	}
	
	@Test
	public void create() throws Exception {
		Mockito.when(userService.saveUser(mockUser)).thenReturn(mockUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/user/")
				.accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}
	
	@Test
	public void update() throws Exception {
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(mockUser);
		Mockito.when(userService.updateUser(mockUser)).thenReturn(mockUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/user/1")
				.accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void delete() throws Exception {
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(mockUser);
		Mockito.doThrow(new RuntimeException()).when(userRepository).delete(Mockito.anyLong());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/user/1")
				.accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void deleteByQuery() throws Exception {
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(mockUser);
		Mockito.doThrow(new RuntimeException()).when(userRepository).deleteUserByQuery(Mockito.anyLong());

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/deletByQuery/user/1")
				.accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
     
	@Test
	public void deleteAll() throws Exception {
		Mockito.doThrow(new RuntimeException()).when(userRepository).deleteAll();

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/api/user/")
				.accept(MediaType.APPLICATION_JSON).content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}

	
