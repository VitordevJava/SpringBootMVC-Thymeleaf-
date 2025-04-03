package projeto.Users.boot.model;

public enum Cargo {

	
	JUNIOR("Junior"),
	PLENO("Pleno"),
	SENIOR("Senior");
	
	 private String nome;
	
	private Cargo(String nome) {
		this.nome = nome;
	}
	
    public void setNome(String nome) {
		this.nome = nome;
	}
    
    public String getNome() {
		return nome;
	}
    
    @Override
    public String toString() {
    	return this.name();
    }
}
