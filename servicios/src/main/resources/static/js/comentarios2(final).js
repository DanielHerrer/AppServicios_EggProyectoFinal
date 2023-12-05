document.addEventListener('DOMContentLoaded', cargarComentarios);

function cargarComentarios() {

    const comentariosLista = document.getElementById('comentarios-lista');
    comentariosLista.innerHTML = '';

    comentariosMock.forEach(comentario => {
        const nuevoComentario = document.createElement('div');
        nuevoComentario.className = 'comentario';
        nuevoComentario.innerHTML = `<strong>${comentario.nombre};</strong> ${comentario.comentario}`;
        comentariosLista.appendChild(nuevoComentario);
    });
}

function enviarComentario() {
    const nombre = document.getElementById('nombre').value;
    const comentario = document.getElementById('comentario').value;

    if (nombre && comentario) {

        const nuevoComentario = document.createElement('div');
        nuevoComentario.className = 'comentario';
        nuevoComentario.innerHTML = `<strong>${nombre}:</strong> ${comentario}`;

        document.getElementById('comentarios-lista').appendChild(nuevoComentario);

        document.getElementById('nombre').value = '';
        document.getElementById('comentario').value = '';
    } else {
        alert('Por favor, completa todos los campos.');
    }
}