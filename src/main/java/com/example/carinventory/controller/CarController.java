package com.example.carinventory.controller;


import com.example.carinventory.dto.CarDto;
import com.example.carinventory.dto.CarList;
import com.example.carinventory.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;

    @Operation(summary = "Get list of filtered cars")
    @GetMapping("/search")
    public String showCarList(@ModelAttribute("carSearchDTO") CarDto carSearchDTO, Model model) {
        List<CarDto> cars = carService.searchCars(carSearchDTO);
        model.addAttribute("cars", cars);
        model.addAttribute("carSearchDTO", carSearchDTO);
        return "car-list";
    }

    @PostMapping("/download")
    public void downloadCars(@ModelAttribute("carSearchDTO") CarDto carSearchDTO, HttpServletResponse response) throws IOException {
        List<CarDto> cars = carService.searchCars(carSearchDTO);
        String xmlData = convertToXml(cars);

        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "attachment; filename=cars.xml");
        response.getOutputStream().write(xmlData.getBytes());
    }

    private String convertToXml(List<CarDto> cars) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CarList.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            marshaller.marshal(new CarList(cars), sw);
            return sw.toString();
        } catch (JAXBException e) {
            log.error("Cannot convert to xml", e);
            return StringUtils.EMPTY;
        }
    }

    @Operation(summary = "Create a new car", description = "Creates a new car with the specified details.")
    @PostMapping
    public String createCar(@ModelAttribute("car") CarDto car, Model model) {
        carService.createCar(car);
        return "redirect:cars/search";
    }
}

