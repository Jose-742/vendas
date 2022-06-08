

/*window.onload = function(){
	//$('#formEndereco').hide();
}*/
$(document).ready(function(){
	$('#cep').mask('00000000',{ reverse: false });
})

$('#buscarCep').on('click', function(){
	let cep = document.getElementById('cep').value
	$.ajax({
			method: "GET",
			url: `https://viacep.com.br/ws/${cep}/json/`,
			beforeSend: function(){
				$('#formEndereco').hide();
				$("#loader-img").show();
			},
			success: function(response){
				formulario(response);
				$("#loader-img").hide();
				//$("#loader-img").removeClass("loader");
			}
			
		})
})


function formulario(response){
	$('#formEndereco').show();
	$('#logradouro').val(response.logradouro)
	$('#bairro').val(response.bairro)
	$('#localidade').val(response.localidade)
	$('#uf').val(response.uf)
	$('#cepInput').val(response.cep)
}

