package org.example;

import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        try (DatabaseConnector connector = new DatabaseConnector()) {
            CountryService countryService = new CountryService(connector.connect());

            System.out.println("All Countries:");
            countryService.displayAllCountries();

            System.out.println("\nCities of Ukraine:");
            countryService.displayCitiesOfCountry("Ukraine");

            System.out.println("\nAll Capitals:");
            countryService.displayAllCapitals();

            System.out.println("\nCapital of Germany:");
            countryService.displayCapitalOfCountry("Germany");

            System.out.println("\nTop 3 Countries by City Count:");
            countryService.displayTop3CountriesByCityCount();

            System.out.println("\nTop 3 Countries by Population:");
            countryService.displayTop3CountriesByPopulation();

            System.out.println("\nBottom 3 Countries by Population:");
            countryService.displayBottom3CountriesByPopulation();

            System.out.println("\nAverage City Population in France:");
            countryService.displayAverageCityPopulation("France");

            System.out.println("\nCity Same Name Count Across Countries:");
            countryService.displayCityNameCountAcrossCountries();

            System.out.println("\nUnique City Names:");
            countryService.displayUniqueCityNames();

            System.out.println("\nCountries with City Count in Range (1-2):");
            countryService.displayCountriesWithCityCountInRange(1, 2);
        }
    }
}
