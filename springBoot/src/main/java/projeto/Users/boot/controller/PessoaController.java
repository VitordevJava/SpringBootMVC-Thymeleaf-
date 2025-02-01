package projeto.Users.boot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import projeto.Users.boot.model.Pessoa;
import projeto.Users.boot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST, value ="*/salvarpessoa" )
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa"); 
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		andView.addObject("pessoas", pessoasIt);
		andView.addObject("pessoaobj", new Pessoa());

		
		return andView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/listapessoas" )
	public ModelAndView pessoas(){ /*Define um método chamado pessoas que retorna um objeto ModelAndView*/
		ModelAndView andView = new ModelAndView("cadastro/cadastropessoa"); /*Cria um objeto ModelAndView que aponta para a view cadastro/cadastropessoa*/
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll(); /*Usa o pessoaRepository para buscar todas as pessoas cadastradas no banco de dados*/
		andView.addObject("pessoas", pessoasIt); /*Adiciona a lista de pessoas (pessoasIt) ao ModelAndView com o nome "pessoas"*/
		andView.addObject("pessoaobj", new Pessoa());
		return andView; /*Retorna o objeto ModelAndView para o Spring MVC*/
	}

	@GetMapping("/editarpessoa/{idpessoa}")
	public ModelAndView editar(@PathVariable ("idpessoa")Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", pessoa.get());
		return modelAndView;
	
	}
}