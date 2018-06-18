package br.com.munieri.boleto.integration;

import br.com.munieri.boleto.BoletoApplication;
import br.com.munieri.boleto.model.Boleto;
import br.com.munieri.boleto.model.Status;
import br.com.munieri.boleto.repository.BoletoRepository;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BoletoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoletoControllerTestIT {

    @Autowired
    BoletoRepository repository;

    @LocalServerPort
    private Integer port;

    @Test
    public void deve_criar_novo_boleto() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        " \"due_date\":\"2019-01-01\",\n" +
                        " \"total_in_cents\":\"100000\",\n" +
                        " \"customer\":\"Trillian Company\",\n" +
                        " \"status\":\"PENDING\"\n" +
                        "}")
                .when()
                .post("http://127.0.0.1:" + port + "/rest/bankslips")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void deve_lancar_excecao_dados_nao_enviados() {
        given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .post("http://127.0.0.1:" + port + "/rest/bankslips")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deve_lancar_excecao_data_invalida() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        " \"due_date\":\"2018-01-01\",\n" +
                        " \"total_in_cents\":\"100000\",\n" +
                        " \"customer\":\"Trillian Company\",\n" +
                        " \"status\":\"PENDING\"\n" +
                        "}")
                .when()
                .post("http://127.0.0.1:" + port + "/rest/bankslips")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    public void deve_lancar_excecao_valor_boleto_invalido() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        " \"due_date\":\"2018-01-01\",\n" +
                        " \"total_in_cents\":\"-100\",\n" +
                        " \"customer\":\"Trillian Company\",\n" +
                        " \"status\":\"PENDING\"\n" +
                        "}")
                .when()
                .post("http://127.0.0.1:" + port + "/rest/bankslips")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    public void deve_lancar_excecao_nome_em_branco() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        " \"due_date\":\"2018-01-01\",\n" +
                        " \"total_in_cents\":\"-100\",\n" +
                        " \"customer\":\"\",\n" +
                        " \"status\":\"PENDING\"\n" +
                        "}")
                .when()
                .post("http://127.0.0.1:" + port + "/rest/bankslips")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    public void deve_lancar_excecao_status_invalido() {
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        " \"due_date\":\"2018-01-01\",\n" +
                        " \"total_in_cents\":\"-100\",\n" +
                        " \"customer\":\"\",\n" +
                        " \"status\":\"TESTE\"\n" +
                        "}")
                .when()
                .post("http://127.0.0.1:" + port + "/rest/bankslips")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void deve_retornar_lista_boletos() {

        given()
                .when()
                .get("http://127.0.0.1:" + port + "/rest/bankslips")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deve_buscar_boleto_por_id() {

        Boleto boleto = criaBoletoParaTestes();

        given()
                .when()
                .get("http://127.0.0.1:" + port + "/rest/bankslips/" + boleto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("customer", equalTo("Joao da Silva"));
    }

    @Test
    public void deve_buscar_boleto_cinco_dias_atrasado() {

        Boleto boleto = criaBoletoCincoDiasAtrasadoParaTestes();

        given()
                .when()
                .get("http://127.0.0.1:" + port + "/rest/bankslips/" + boleto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("customer", equalTo("Joao da Silva"))
                .and()
                .body("fine", equalTo("5.0"));
    }

    @Test
    public void deve_buscar_boleto_dez_dias_atrasado() {

        Boleto boleto = criaBoletoDezDiasAtrasadoParaTestes();

        given()
                .when()
                .get("http://127.0.0.1:" + port + "/rest/bankslips/" + boleto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("customer", equalTo("Joao da Silva"))
                .and()
                .body("fine", equalTo("10.0"));
    }

    @Test
    public void deve_atualizar_boleto_para_pago() {

        Boleto boleto = criaBoletoParaTestes();

        given()
                .contentType(ContentType.JSON)
                .body("{\"status\": \"PAID\"}")
                .when()
                .put("http://127.0.0.1:" + port + "/rest/bankslips/" + boleto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deve_atualizar_boleto_para_cancelado() {

        Boleto boleto = criaBoletoParaTestes();

        given()
                .contentType(ContentType.JSON)
                .body("{\"status\": \"CANCELED\"}")
                .when()
                .put("http://127.0.0.1:" + port + "/rest/bankslips/" + boleto.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    private Boleto criaBoletoParaTestes() {
        Boleto boleto = repository.save(new Boleto(LocalDate.now(), BigDecimal.TEN, "Joao da Silva", Status.PENDING));
        return boleto;
    }

    private Boleto criaBoletoCincoDiasAtrasadoParaTestes() {
        Boleto boleto = repository.save(new Boleto(LocalDate.now().minusDays(5), new BigDecimal(100), "Joao da Silva", Status.PENDING));
        return boleto;
    }

    private Boleto criaBoletoDezDiasAtrasadoParaTestes() {
        Boleto boleto = repository.save(new Boleto(LocalDate.now().minusDays(10), new BigDecimal(100), "Joao da Silva", Status.PENDING));
        return boleto;
    }
}
