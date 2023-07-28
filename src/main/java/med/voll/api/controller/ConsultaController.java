package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentosConsulta;
import med.voll.api.domain.consulta.DadosCacelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentosConsulta dados){
        var dto = agenda.agendar(dados);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/cancelamento")
    @Transactional
    public ResponseEntity<String> cancelarConsulta(@RequestBody @Valid DadosCacelamentoConsulta dados){
        agenda.cancelar(dados);
        var mensagemDeSucesso = "Deu boa";

        return new ResponseEntity<>(mensagemDeSucesso, HttpStatus.NO_CONTENT);
    }
}
