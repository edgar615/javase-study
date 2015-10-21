package com.edgar.module.sys.web;
//package com.edgar.module.sys.web;
//
//import java.util.ArrayList;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.edgar.module.sys.repository.domain.SysDict;
//import com.edgar.module.sys.service.SysDictService;
//import com.edgar.repository.Pagination;
//import com.edgar.repository.QueryExample;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration("classpath:spring/mvc-config.xml")
//public class SysDictResourceTest {
//
//        private MockMvc mockMvc;
//        @Rule
//        public JUnitRuleMockery context = new JUnitRuleMockery();
//        private SysDictService sysDictService;
//
//        @Before
//        public void setUp() {
//                SysDictResource sysDictResource = new SysDictResource();
//                sysDictService = context.mock(SysDictService.class);
//                sysDictResource.setSysDictService(sysDictService);
//                mockMvc = MockMvcBuilders.standaloneSetup(sysDictResource).build();
//        }
//
//        @Test
//        public void testView() throws Exception {
//                final Pagination<SysDict> pagination = Pagination.newInstance(1, 10, 15,
//                                new ArrayList<SysDict>());
//                context.checking(new Expectations() {
//                        {
//                                oneOf(sysDictService).pagination(with(any(QueryExample.class)),
//                                                with(same(1)), with(same(10)));
//                                will(returnValue(pagination));
//                        }
//                });
//
//                // 1、mockMvc.perform执行一个请求；
//                // 2、MockMvcRequestBuilders.get("/user/1")构造一个请求
//                // 3、ResultActions.andExpect添加执行完成后的断言
//                // 4、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
//                // 5、ResultActions.andReturn表示执行完成后返回相应的结果。
//                // MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
//                // .andExpect(MockMvcResultMatchers.view().name("user/view"))
//                // .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
//                // .andDo(MockMvcResultHandlers.print())
//                // .andReturn();
//                //
//                // Assert.assertNotNull(result.getModelAndView().getModel().get("user"));
//
//                // mockMvc.perform(MockMvcRequestBuilders.get("/sys/dict/paginaton")
//                // .contentType(MediaType.APPLICATION_JSON).content(requestBody)
//                // .accept(MediaType.APPLICATION_JSON))
//                // .andExpect(MockMvcResultMatchers.content().contentType(
//                // MediaType.APPLICATION_JSON))
//                // .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1)).andDo(MockMvcResultHandlers.print())
//                // .andReturn();
//
//                MvcResult result = mockMvc
//                                .perform(MockMvcRequestBuilders.get("/sys/dict/pagination")
//                                                .param("page", "1")
//                                                .contentType(MediaType.APPLICATION_JSON)
//                                                .accept(MediaType.APPLICATION_JSON))
//                                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRecords").value(
//                                                15))
//                                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(10))
//                                .andExpect(MockMvcResultMatchers.jsonPath("$.page").value(1))
//                                .andDo(MockMvcResultHandlers.print()).andReturn();
//                Assert.assertNotNull(result.getResponse());
//        }
//
//        @Test
//        public void testGet() throws Exception {
//                final SysDict sysDict = new SysDict();
//                sysDict.setDictCode("11");
//                context.checking(new Expectations() {
//                        {
//                                oneOf(sysDictService).get(with(any(String.class)));
//                                will(returnValue(sysDict));
//                        }
//                });
//
//                MvcResult result = mockMvc
//                                .perform(MockMvcRequestBuilders.get("/sys/dict/{dictCode}", "11")
//                                                .contentType(MediaType.APPLICATION_JSON)
//                                                .accept(MediaType.APPLICATION_JSON))
//                                .andExpect(MockMvcResultMatchers.jsonPath("$.dictCode").value(
//                                                sysDict.getDictCode()))
//                                                .andExpect(MockMvcResultMatchers.jsonPath("$.dictCode").value(
//                                                                sysDict.getDictCode()))
//                                                                .andExpect(MockMvcResultMatchers.jsonPath("$.dictName").value(
//                                                                                sysDict.getDictName()))
//                                .andDo(MockMvcResultHandlers.print()).andReturn();
//                Assert.assertNotNull(result.getResponse());
//        }
//}
