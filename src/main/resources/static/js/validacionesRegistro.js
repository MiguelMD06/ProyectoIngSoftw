document
  .getElementById("formRegistroAdmin")
  .addEventListener("submit", function (e) {
    let valido = true;

    // LIMPIAR ERRORES
    document.querySelectorAll(".error").forEach((el) => (el.innerText = ""));

    // CAMPOS
    let tipoId = document.getElementById("tipoIdentificacion").value;
    let numeroId = document.getElementById("numeroIdentificacion").value.trim();
    let primerNombre = document.getElementById("primerNombre").value.trim();
    let primerApellido = document.getElementById("primerApellido").value.trim();
    let celular = document.getElementById("celular").value.trim();
    let institucion = document.getElementById("institucion").value.trim();
    let salon = document.getElementById("salon").value.trim();
    let rol = document.getElementById("rol").value;

    // VALIDACIONES

    if (tipoId === "") {
      document.getElementById("errorTipoId").innerText =
        "Seleccione un tipo de identificación";
      valido = false;
    }

    if (numeroId === "") {
      document.getElementById("errorNumeroId").innerText =
        "Ingrese número de identificación";
      valido = false;
    }

    if (primerNombre === "") {
      document.getElementById("errorPrimerNombre").innerText =
        "Ingrese primer nombre";
      valido = false;
    }

    if (primerApellido === "") {
      document.getElementById("errorPrimerApellido").innerText =
        "Ingrese primer apellido";
      valido = false;
    }

    if (celular.length < 10) {
      document.getElementById("errorCelular").innerText =
        "Número celular inválido";
      valido = false;
    }

    if (institucion === "") {
      document.getElementById("errorInstitucion").innerText =
        "Ingrese institución";
      valido = false;
    }

    if (salon === "") {
      document.getElementById("errorSalon").innerText = "Ingrese salón";
      valido = false;
    }

    if (rol === "") {
      document.getElementById("errorRol").innerText = "Seleccione un rol";
      valido = false;
    }

    if (!valido) {
      e.preventDefault();
    }
  });
