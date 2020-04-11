package core.nmvc;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import was.http.HttpRequest;
import was.http.HttpResponse;

@Controller
public class MyController {
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @RequestMapping("/users")
    public String list(HttpRequest request, HttpResponse response) {
        logger.debug("users findUserId");
        return "/users/list";
    }

    @RequestMapping(value="/users/show", method= RequestMethod.GET)
    public String show(HttpRequest request, HttpResponse response) {
        logger.debug("users findUserId");
        return "/users/show";
    }

    @RequestMapping(value="/users", method=RequestMethod.POST)
    public String create(HttpRequest request, HttpResponse response) {
        logger.debug("users create");
        return "redirect:/users";
    }
}