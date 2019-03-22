package amazing.co.controllers;


import amazing.co.config.CompanyCustomEditor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
public abstract class BaseControllerTest {

    @MockBean
    private CompanyCustomEditor customEditor;

    @Autowired
    MockMvc mvc;

}
