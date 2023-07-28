package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentosConsulta;

public interface ValidadorAgendamentoDeConsulta {
    void validar(DadosAgendamentosConsulta dados);
}
