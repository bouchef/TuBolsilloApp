package com.example.bouchef.tubolsillo.api.model;

import java.util.List;

public class ApiResponseAppointments {
    private List<MensajeViewModelResponse> results;

    public ApiResponseAppointments(List<MensajeViewModelResponse> results) {
        this.results = results;
    }

    public List<MensajeViewModelResponse> getResults() {
        return results;
    }
}