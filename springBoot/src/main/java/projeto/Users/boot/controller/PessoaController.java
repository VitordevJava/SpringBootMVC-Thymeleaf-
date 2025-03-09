package projeto.Users.boot.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
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
    
    @Autowired
    private ReportUtil reportUtil;

    @RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
    public ModelAndView inicio() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }

    @PostMapping("/salvarpessoa")
    public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            modelAndView.addObject("pessoas", pessoaRepository.findAll());
            modelAndView.addObject("pessoaobj", pessoa);

            List<String> msg = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage());
            }
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

       
        if (pessoa.getId() != null && pessoaRepository.existsById(pessoa.getId())) {
            // Se o ID já existe, redireciona para evitar sobrescrever por engano
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            modelAndView.addObject("pessoas", pessoaRepository.findAll());
            modelAndView.addObject("pessoaobj", pessoa);
            modelAndView.addObject("msg", List.of("Pessoa já existe. Use a opção de edição."));
            return modelAndView;
        }

        pessoaRepository.save(pessoa);

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }
    /*Metodo de edição, simplificação do codigo de atualização, 
     * (SE HOUVER ID SERÁ REDIRECIONADO PARA ESSE METODO, a validação é feita no /salvar usuario)*/
    @PostMapping("/atualizarpessoa")
    public ModelAndView atualizar(@Valid Pessoa pessoa, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            modelAndView.addObject("pessoas", pessoaRepository.findAll());
            modelAndView.addObject("pessoaobj", pessoa);

            List<String> msg = new ArrayList<>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage());
            }
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

        /* Verifica se a pessoa existe antes de atualizar */
        if (pessoa.getId() == null || !pessoaRepository.existsById(pessoa.getId())) {
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            modelAndView.addObject("pessoas", pessoaRepository.findAll());
            modelAndView.addObject("pessoaobj", pessoa);
            modelAndView.addObject("msg", List.of("Pessoa não encontrada para edição."));
            return modelAndView;
        }

        pessoaRepository.save(pessoa); /* Atualiza a pessoa existente */

        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
        modelAndView.addObject("pessoaobj", new Pessoa());
        modelAndView.addObject("msg", List.of("Pessoa atualizada com sucesso!"));
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/listapessoas")
    public ModelAndView pessoas() {
        ModelAndView andView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
        andView.addObject("pessoas", pessoasIt);
        andView.addObject("pessoaobj", new Pessoa());
        return andView;
    }

    @GetMapping("/editarpessoa/{idpessoa}")
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
        if (pessoa.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            modelAndView.addObject("pessoaobj", pessoa.get());
            modelAndView.addObject("pessoas", pessoaRepository.findAll());
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/listapessoas");
        }
    }

    @GetMapping("/removerpessoa/{idpessoa}")
    public ModelAndView remover(@PathVariable("idpessoa") Long idpessoa) {
        pessoaRepository.deleteById(idpessoa);
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }

    @GetMapping("/pesquisarpessoa")
    public String redirecionarPesquisa() {
        return "redirect:/cadastropessoa";
    }

    @PostMapping("/pesquisarpessoa")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa, 
                                  @RequestParam("pesqsexo") String pesqsexo,
                                  HttpSession session) {
        List<Pessoa> pessoas = pesqsexo != null && !pesqsexo.isEmpty() 
            ? pessoaRepository.findPessoaByNameSexo(nomepesquisa, pesqsexo)
            : pessoaRepository.findPessoaByName(nomepesquisa);
        
        session.setAttribute("pessoasPesquisadas", pessoas);
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoas);
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }

    @GetMapping("/imprimirPDF")
    @Transactional(readOnly = true)
    public void imprimePDF(HttpSession session, HttpServletResponse response) throws Exception {
        List<Pessoa> pessoas = (List<Pessoa>) session.getAttribute("pessoasPesquisadas");

        if (pessoas == null) {
            pessoas = new ArrayList<>();
            for (Pessoa pessoa : pessoaRepository.findAll()) {
                Hibernate.initialize(pessoa.getTelefones());
                pessoas.add(pessoa);
            }
        } else {
            List<Pessoa> refreshedPessoas = new ArrayList<>();
            for (Pessoa pessoa : pessoas) {
                Pessoa refreshedPessoa = pessoaRepository.findById(pessoa.getId()).orElse(pessoa);
                Hibernate.initialize(refreshedPessoa.getTelefones());
                refreshedPessoas.add(refreshedPessoa);
            }
            pessoas = refreshedPessoas;
        }

        byte[] pdf = reportUtil.gerarRelatorio(pessoas, "pessoa", session.getServletContext());
        response.setContentLength(pdf.length);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
        response.setHeader(headerKey, headerValue);
        response.getOutputStream().write(pdf);
    }

    
    @GetMapping("/telefones/{idpessoa}")
    public ModelAndView telefone(@PathVariable("idpessoa") Long idpessoa) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa.get());
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
        return modelAndView;
    }

    @PostMapping("/addfonePessoa/{pessoaid}")
    public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
        Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
        if (telefone != null && (telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty())) {
            ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
            modelAndView.addObject("pessoaobj", pessoa);
            modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
            List<String> msg = new ArrayList<>();
            if (telefone.getNumero().isEmpty()) {
                msg.add("Número deve ser Informado");
            }
            if (telefone.getTipo().isEmpty()) {
                msg.add("Tipo deve ser informado");
            }
            modelAndView.addObject("msg", msg);
            return modelAndView;
        }

        telefone.setPessoa(pessoa);
        telefoneRepository.save(telefone);

        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa);
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
        return modelAndView;
    }

    @GetMapping("/removertelefone/{idtelefone}")
    public ModelAndView removertelefone(@PathVariable("idtelefone") Long idtelefone) {
        Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
        telefoneRepository.deleteById(idtelefone);
        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa);
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
        return modelAndView;
    }
    
    
    
    
    
    
    
}