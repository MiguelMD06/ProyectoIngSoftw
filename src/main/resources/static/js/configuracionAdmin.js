const modalCambiar = document.getElementById('modalCambiarPassword');
modalCambiar.addEventListener('show.bs.modal', function (e) {
  const btn = e.relatedTarget;
  document.getElementById('modalUserId').value       = btn.dataset.id;
  document.getElementById('modalUsername').textContent = btn.dataset.username;
  document.getElementById('modalNueva').value        = '';
  document.getElementById('modalConfirmar').value    = '';
  document.getElementById('msgModalConfirmar').classList.add('d-none');
});

const modalNueva     = document.getElementById('modalNueva');
const modalConfirmar = document.getElementById('modalConfirmar');
const msgModalError  = document.getElementById('msgModalConfirmar');

function validarModal() {
  if (modalConfirmar.value && modalNueva.value !== modalConfirmar.value) {
    msgModalError.classList.remove('d-none');
    modalConfirmar.classList.add('is-invalid');
  } else {
    msgModalError.classList.add('d-none');
    modalConfirmar.classList.remove('is-invalid');
  }
}

modalNueva.addEventListener('input', validarModal);
modalConfirmar.addEventListener('input', validarModal);

document.getElementById('formCambioAdmin').addEventListener('submit', function (e) {
  if (modalNueva.value !== modalConfirmar.value) {
    e.preventDefault();
    validarModal();
  }
});

const filtroRol         = document.getElementById('filtroRol');
const filtroInstitucion = document.getElementById('filtroInstitucion');
const filas             = document.querySelectorAll('#tablaUsuarios tbody tr:not(#sinResultados)');
const sinResultados     = document.getElementById('sinResultados');

function aplicarFiltros() {
  const rol  = filtroRol.value;
  const inst = filtroInstitucion.value;
  let visibles = 0;

  filas.forEach(function (fila) {
    const filRol  = !rol  || fila.dataset.rol === rol;
    const filInst = !inst || fila.dataset.institucion === inst;
    const mostrar = filRol && filInst;
    fila.style.display = mostrar ? '' : 'none';
    if (mostrar) visibles++;
  });

  sinResultados.style.display = visibles === 0 ? '' : 'none';
}

filtroRol.addEventListener('change', aplicarFiltros);
filtroInstitucion.addEventListener('change', aplicarFiltros);
