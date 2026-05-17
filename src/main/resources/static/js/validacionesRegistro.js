const REGEX = {
  // Solo letras (incluye tildes y ñ), sin números ni caracteres especiales
  nombre: /^[A-Za-záéíóúÁÉÍÓÚüÜñÑ]+(\s[A-Za-záéíóúÁÉÍÓÚüÜñÑ]+)*$/,

  // Celular colombiano: empieza en 3, exactamente 10 dígitos
  celular: /^3\d{9}$/,

  // Salón: solo 1 o 2
  salon: /^[12]$/,

  // Documento por tipo de identificación colombiano
  doc: {
    RI:  /^\d{6,11}$/,        // Registro Civil: 6-11 dígitos
    TI:  /^\d{10,11}$/,       // Tarjeta de Identidad: 10-11 dígitos
    CC:  /^\d{5,10}$/,        // Cédula de Ciudadanía: 5-10 dígitos
    CE:  /^[A-Za-z0-9]{4,12}$/, // Cédula de Extranjería: alfanumérico 4-12
    PP:  /^[A-Za-z0-9]{5,9}$/,  // Pasaporte: alfanumérico 5-9
    PEP: /^[A-Za-z0-9]{5,15}$/, // PEP: alfanumérico 5-15
  },
};

const MSG_DOC = {
  RI:  'Registro Civil: 6 a 11 dígitos numéricos.',
  TI:  'Tarjeta de Identidad: 10 u 11 dígitos numéricos.',
  CC:  'Cédula de Ciudadanía: 5 a 10 dígitos numéricos.',
  CE:  'Cédula de Extranjería: 4 a 12 caracteres alfanuméricos.',
  PP:  'Pasaporte: 5 a 9 caracteres alfanuméricos.',
  PEP: 'PEP: 5 a 15 caracteres alfanuméricos.',
};

/* ── helpers ─────────────────────────────────────────── */
function setError(id, msg) {
  const el = document.getElementById(id);
  if (!el) return;
  el.innerText = msg;
}

function markField(inputId, valid) {
  const el = document.getElementById(inputId);
  if (!el) return;
  el.classList.toggle('is-valid',   valid);
  el.classList.toggle('is-invalid', !valid);
}

function clearField(inputId, errorId) {
  const inp = document.getElementById(inputId);
  if (inp) { inp.classList.remove('is-valid', 'is-invalid'); }
  const err = document.getElementById(errorId);
  if (err) err.innerText = '';
}

/* ── validaciones individuales ────────────────────────── */
function validarNombre(inputId, errorId, obligatorio) {
  const val = document.getElementById(inputId).value.trim();
  if (val === '') {
    if (obligatorio) {
      setError(errorId, 'Este campo es obligatorio.');
      markField(inputId, false);
      return false;
    }
    clearField(inputId, errorId);
    return true;
  }
  if (!REGEX.nombre.test(val)) {
    setError(errorId, 'Solo se permiten letras y tildes. Sin números ni caracteres especiales.');
    markField(inputId, false);
    return false;
  }
  setError(errorId, '');
  markField(inputId, true);
  return true;
}

function validarDocumento() {
  const tipo   = document.getElementById('tipoIdentificacion').value;
  const numero = document.getElementById('numeroIdentificacion').value.trim();
  const errorId = 'errorNumeroId';

  if (!tipo) {
    setError(errorId, 'Seleccione primero el tipo de identificación.');
    markField('numeroIdentificacion', false);
    return false;
  }
  if (numero === '') {
    setError(errorId, 'Ingrese el número de identificación.');
    markField('numeroIdentificacion', false);
    return false;
  }
  if (!REGEX.doc[tipo] || !REGEX.doc[tipo].test(numero)) {
    setError(errorId, MSG_DOC[tipo] || 'Formato de documento inválido.');
    markField('numeroIdentificacion', false);
    return false;
  }
  setError(errorId, '');
  markField('numeroIdentificacion', true);
  return true;
}

function validarCelular() {
  const val = document.getElementById('celular').value.trim();
  if (val === '') {
    setError('errorCelular', 'El celular es obligatorio.');
    markField('celular', false);
    return false;
  }
  if (!/^\d+$/.test(val)) {
    setError('errorCelular', 'El celular solo debe contener dígitos.');
    markField('celular', false);
    return false;
  }
  if (!val.startsWith('3')) {
    setError('errorCelular', 'El celular debe comenzar con 3.');
    markField('celular', false);
    return false;
  }
  if (val.length !== 10) {
    setError('errorCelular', 'El celular debe tener exactamente 10 dígitos.');
    markField('celular', false);
    return false;
  }
  setError('errorCelular', '');
  markField('celular', true);
  return true;
}

function validarSelect(selectId, errorId, msg) {
  const val = document.getElementById(selectId).value;
  if (!val || val === '') {
    setError(errorId, msg);
    markField(selectId, false);
    return false;
  }
  setError(errorId, '');
  markField(selectId, true);
  return true;
}

function validarSalon() {
  const val = document.getElementById('salon').value.trim();
  if (val === '') {
    setError('errorSalon', 'El salón es obligatorio.');
    markField('salon', false);
    return false;
  }
  if (!REGEX.salon.test(val)) {
    setError('errorSalon', 'El salón debe ser 1 o 2.');
    markField('salon', false);
    return false;
  }
  setError('errorSalon', '');
  markField('salon', true);
  return true;
}

/* ── listeners en tiempo real ─────────────────────────── */
document.getElementById('tipoIdentificacion')
  .addEventListener('change', () => {
    validarSelect('tipoIdentificacion', 'errorTipoId', 'Seleccione un tipo de identificación.');
    // re-validar documento si ya tiene valor
    if (document.getElementById('numeroIdentificacion').value.trim()) validarDocumento();
  });

document.getElementById('numeroIdentificacion')
  .addEventListener('input', validarDocumento);

document.getElementById('primerNombre')
  .addEventListener('input', () => validarNombre('primerNombre', 'errorPrimerNombre', true));

document.getElementById('segundoNombre')
  .addEventListener('input', () => validarNombre('segundoNombre', 'errorSegundoNombre', false));

document.getElementById('primerApellido')
  .addEventListener('input', () => validarNombre('primerApellido', 'errorPrimerApellido', true));

document.getElementById('segundoApellido')
  .addEventListener('input', () => validarNombre('segundoApellido', 'errorSegundoApellido', false));

document.getElementById('celular')
  .addEventListener('input', validarCelular);

document.getElementById('salon')
  .addEventListener('input', validarSalon);

/* ── submit ───────────────────────────────────────────── */
document.getElementById('formRegistroAdmin')
  .addEventListener('submit', function (e) {
    const resultados = [
      validarSelect('tipoIdentificacion', 'errorTipoId',    'Seleccione un tipo de identificación.'),
      validarDocumento(),
      validarNombre('primerNombre',    'errorPrimerNombre',  true),
      validarNombre('segundoNombre',   'errorSegundoNombre', false),
      validarNombre('primerApellido',  'errorPrimerApellido', true),
      validarNombre('segundoApellido', 'errorSegundoApellido', false),
      validarCelular(),
      validarSelect('institucion', 'errorInstitucion', 'Seleccione una institución.'),
      validarSalon(),
      validarSelect('rol', 'errorRol', 'Seleccione un rol.'),
    ];

    if (resultados.includes(false)) {
      e.preventDefault();
      // Scroll al primer campo inválido
      const primero = document.querySelector('.is-invalid');
      if (primero) primero.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
  });
