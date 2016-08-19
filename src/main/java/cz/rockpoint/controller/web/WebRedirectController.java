package cz.rockpoint.controller.web;

import cz.rockpoint.controller.IMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static cz.rockpoint.controller.IMapping.HOME;

// Redirect controller; used to help with URLs and redirects
@Controller
@RequestMapping(value = HOME)
public class WebRedirectController implements IMapping {

    //region Redirects
    @RequestMapping(value = HOME)
    public String redirectFromHome() {
        return "redirect:/web/competitions/long";
    }

    @RequestMapping(value = COMPETITIONS)
    public String redirectsFromComps(){
        return "redirect:/web/competitions/long";
    }

    @RequestMapping(value = COMPETITIONS + LONG)
    public String redirectFromCompsLong(){
        return "redirect:/web/competitions/long";
    }

    @RequestMapping(value = COMPETITIONS + HALF)
    public String redirectFromCompsHalf(){
        return "redirect:/web/competitions/half";
    }

    @RequestMapping(value = COMPETITIONS + SHORT)
    public String redirectFromCompsShort(){
        return "redirect:/web/competitions/short";
    }

    @RequestMapping(value = LONG)
    public String redirectFromHomeLong(){
        return "redirect:/web/competitions/long";
    }

    @RequestMapping(value = HALF)
    public String redirectFromHomeHalf(){
        return "redirect:/web/competitions/half";
    }

    @RequestMapping(value = SHORT)
    public String redirectFromHomeShort(){
        return "redirect:/web/competitions/short";
    }

    @RequestMapping(value = REGISTRATION_FORM)
    public String redirectFromHomeRRF() { return "redirect:/web/race/registration_form"; }

    @RequestMapping(value = RACE + ALL)
    public String redirectFromRaceAll() { return "redirect:/web/race/all"; }

    @RequestMapping(value = WEB)
    public String redirectFromWeb(){ return "redirect:/web/competitions/long"; }

    @RequestMapping(value = WEB + REGISTRATION_FORM)
    public String redirectFromWebRRF() { return "redirect:/web/race/registration_form"; }
    //endregion
}
