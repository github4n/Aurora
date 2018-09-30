package me.aurora.app.rest.web;

import lombok.extern.slf4j.Slf4j;
import me.aurora.annotation.Log;
import me.aurora.domain.ResponseEntity;
import me.aurora.util.AuroraConstant;
import me.aurora.util.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("weather")
public class WeatherController {


    @Log("获取天气信息")
    @GetMapping(value = "/index")
    public ModelAndView weather() {
        return new ModelAndView("/web/weather/weather");
    }

    @GetMapping(value = "/query")
    public ResponseEntity queryWeather(@RequestParam String areaId) {
        try {
            String data = HttpUtils.sendPost(AuroraConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            log.error("查询天气失败", e.getMessage());
            return ResponseEntity.error("查询天气失败，请联系网站管理员！");
        }
    }
}
