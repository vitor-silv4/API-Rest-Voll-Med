package med.voll.api.domain.consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosDetalhamentoConsuta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public DadosDetalhamentoConsuta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
