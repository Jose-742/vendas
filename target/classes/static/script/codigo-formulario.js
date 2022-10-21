/*//mascara da pagina do formulario de login

/*VISUALIZAR A SENHA DO INPUT */
let mostrar = document.getElementById("mostrar")

let inputSenha = document.getElementById("senha")

if(mostrar != null){
	mostrar.addEventListener('click', function(){
	if(inputSenha.type == "password"){
		inputSenha.type = "text"
	}else{
		inputSenha.type = "password";
	}
});
}

$(document).ready(function(){
	$('#cpf').mask('000.000.000-00', { reverse: true });
})
