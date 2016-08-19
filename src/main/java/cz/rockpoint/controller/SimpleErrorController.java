package cz.rockpoint.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

// Controller that handles with errors
@Controller
public class SimpleErrorController implements ErrorController, IMapping {

    @Override
    public String getErrorPath() {
        return ERROR;
    }

    @RequestMapping(value = ERROR)
    public ModelAndView gotError() {
        return new ModelAndView("error");
    }
}
