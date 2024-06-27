package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class CountryService {

    private Connection connection;

    @SneakyThrows
    public void displayAllCountries() {
        String query = """
                SELECT "Countries"."name", "Cities"."name" as capital, "Countries"."population" 
                FROM "Countries" 
                LEFT JOIN "Cities" ON "Countries"."capital_id" = "Cities"."id"
                """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println(
                        "Country: " + rs.getString("name") + ", Capital: " + rs.getString("capital")
                                + ", Population: " + rs.getLong("population"));
            }
        }
    }

    @SneakyThrows
    public void displayCitiesOfCountry(String countryName) {
        String query = """
                SELECT "Cities"."name", "Cities"."population"
                FROM "Cities"
                INNER JOIN "Countries" ON "Cities"."country_id" = "Countries"."id"
                WHERE "Countries"."name" = '""" + countryName + "'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println(
                        "City: " + rs.getString("name") + ", Population: " + rs.getLong(
                                "population"));
            }
        }
    }

    @SneakyThrows
    public void displayAllCapitals() {
        String query = """
                SELECT "Cities"."name" as capital
                FROM "Cities"
                INNER JOIN "Countries" ON "Cities"."id" = "Countries"."capital_id"
                """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println("Capital: " + rs.getString("capital"));
            }
        }
    }

    @SneakyThrows
    public void displayCapitalOfCountry(String countryName) {
        String query = """
                SELECT "Cities"."name" as capital
                FROM "Cities"
                INNER JOIN "Countries" ON "Cities"."id" = "Countries"."capital_id"
                WHERE "Countries"."name" = '""" + countryName + "'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            if (rs.next()) {
                System.out.println("Capital of " + countryName + ": " + rs.getString("capital"));
            }
        }
    }

    public void displayTop3CountriesByCityCount() throws SQLException {
        String query = """
                SELECT "Countries"."name", COUNT("Cities"."id") as city_count
                FROM "Countries"
                LEFT JOIN "Cities" ON "Countries"."id" = "Cities"."country_id"
                GROUP BY "Countries"."name"
                ORDER BY city_count DESC
                LIMIT 3
                """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println(
                        "Country: " + rs.getString("name") + ", City Count: " + rs.getInt(
                                "city_count"));
            }
        }
    }

    public void displayTop3CountriesByPopulation() throws SQLException {
        String query = """
                SELECT "name", "population"
                FROM "Countries"
                ORDER BY "population" DESC
                LIMIT 3
                """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println(
                        "Country: " + rs.getString("name") + ", Population: " + rs.getLong(
                                "population"));
            }
        }
    }

    public void displayBottom3CountriesByPopulation() throws SQLException {
        String query = """
                SELECT "name", "population"
                FROM "Countries"
                ORDER BY "population" ASC
                LIMIT 3
                """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println(
                        "Country: " + rs.getString("name") + ", Population: " + rs.getLong(
                                "population"));
            }
        }
    }

    public void displayAverageCityPopulation(String countryName) throws SQLException {
        String query = """
                SELECT AVG("Cities"."population") as avg_population
                FROM "Cities"
                INNER JOIN "Countries" ON "Cities"."country_id" = "Countries"."id"
                WHERE "Countries"."name" = '""" + countryName + "'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            if (rs.next()) {
                System.out.println(
                        "Average City Population in " + countryName + ": " + rs.getDouble(
                                "avg_population"));
            }
        }
    }

    public void displayCityNameCountAcrossCountries() throws SQLException {
        String query = """
                SELECT "name", COUNT("name") as name_count
                FROM "Cities"
                GROUP BY "name"
                HAVING COUNT("name") > 1
                """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println(
                        "City Name: " + rs.getString("name") + ", Count Across Countries: "
                                + rs.getInt("name_count"));
            }
        }
    }

    public void displayUniqueCityNames() throws SQLException {
        String query = """
                SELECT DISTINCT "name"
                FROM "Cities"
                """;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println("Unique City Name: " + rs.getString("name"));
            }
        }
    }

    public void displayCountriesWithCityCountInRange(int min, int max) throws SQLException {
        String query = """
                SELECT "Countries"."name", COUNT("Cities"."id") as city_count
                FROM "Countries"
                LEFT JOIN "Cities" ON "Countries"."id" = "Cities"."country_id"
                GROUP BY "Countries"."name"
                HAVING COUNT("Cities"."id") BETWEEN\s""" + min + " AND " + max;
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(
                query)) {
            while (rs.next()) {
                System.out.println(
                        "Country: " + rs.getString("name") + ", City Count: " + rs.getInt(
                                "city_count"));
            }
        }
    }
}
