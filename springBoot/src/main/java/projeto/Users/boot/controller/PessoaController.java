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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import projeto.Users.boot.model.Pessoa;
import projeto.Users.boot.model.Telefone;
import projeto.Users.boot.repository.PessoaRepository;
import projeto.Users.boot.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private TelefoneRepository telefoneRepository;

	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll()); /*Atualiza a lista de pessoas*/
        modelAndView.addObject("pessoaobj", new Pessoa());  
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}
	
	@PostMapping("*/salvarpessoa")  /*Mudança no metodo otimizando a sixtaxe, e usando direto a anotação de @PostMapping*/
    public ModelAndView salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa); 

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll()); /*Atualiza a lista de pessoas*/
        modelAndView.addObject("pessoaobj", new Pessoa());  
        return modelAndView;
        
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
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
	    Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
	    if (pessoa.isPresent()) {
	        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
	        modelAndView.addObject("pessoaobj", pessoa.get());
	        return modelAndView;
	    } else {
	        return new ModelAndView("redirect:/listapessoas");
	    }
	}
	@GetMapping("/removerpessoa/{idpessoa}")
	public ModelAndView remover(@PathVariable("idpessoa") Long idpessoa) { 
	    pessoaRepository.deleteById(idpessoa); /* Remove a pessoa pelo ID do banco de dados */
	    ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa"); /* Define a view de cadastro */
	    modelAndView.addObject("pessoas", pessoaRepository.findAll()); /* Adiciona a lista atualizada de pessoas à view */
	    modelAndView.addObject("pessoaobj", new Pessoa()); /* Adiciona um novo objeto Pessoa vazio à view */
	    return modelAndView; /* Retorna a view com a lista atualizada e um novo objeto Pessoa */
	}
	
	  @GetMapping("/pesquisarpessoa") // Redireciona se acessado diretamente
	    public String redirecionarPesquisa() {
	        return "redirect:/cadastropessoa";
	    }
	@PostMapping("/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
	}
	@GetMapping("/telefones/{idpessoa}")
	public ModelAndView telefone(@PathVariable("idpessoa") Long idpessoa) { 
	    Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa); /*Busca a pessoa pelo ID no banco de dados */
	    
	    ModelAndView modelAndView = new ModelAndView("cadastro/telefones"); /* Define a view de cadastro */
	    modelAndView.addObject("pessoaobj", pessoa.get()); /* Adiciona a pessoa encontrada à view */
	    return modelAndView; /* Retorna a view com os dados da pessoa */
	}
	
	@PostMapping("/addfonePessoa/{pessoaid}")
	public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
		
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoaobj", pessoa);
		return modelAndView;
	}
}