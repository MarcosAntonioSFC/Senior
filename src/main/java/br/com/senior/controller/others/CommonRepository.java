package br.com.senior.controller.others;

import br.com.senior.model.abstracts.AbstractModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Classe com extensão do repositório para paginação e outros recursos.
 *
 * @param <T> T extends common model.
 */
@NoRepositoryBean
public interface CommonRepository<T extends AbstractModel> extends JpaRepository<T, String> {

}
