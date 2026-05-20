function toggleVer(inputId, btn) {
  const input = document.getElementById(inputId);
  const icon  = btn.querySelector('i');
  if (input.type === 'password') {
    input.type = 'text';
    icon.className = 'bi bi-eye-slash';
  } else {
    input.type = 'password';
    icon.className = 'bi bi-eye';
  }
}

const nueva     = document.getElementById('passwordNueva');
const confirmar = document.getElementById('confirmarPassword');
const msgError  = document.getElementById('msgConfirmar');
const form      = document.getElementById('formPassword');

function validarCoincidencia() {
  if (confirmar.value && nueva.value !== confirmar.value) {
    msgError.classList.remove('d-none');
    confirmar.classList.add('is-invalid');
  } else {
    msgError.classList.add('d-none');
    confirmar.classList.remove('is-invalid');
  }
}

nueva.addEventListener('input', validarCoincidencia);
confirmar.addEventListener('input', validarCoincidencia);

form.addEventListener('submit', function (e) {
  if (nueva.value !== confirmar.value) {
    e.preventDefault();
    validarCoincidencia();
  }
});
