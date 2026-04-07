// ============================================
// API BASE & UTILIDADES
// ============================================
const API = 'http://localhost:8080/api';

async function apiFetch(endpoint, options = {}) {
    try {
        const res = await fetch(`${API}${endpoint}`, {
            headers: { 'Content-Type': 'application/json' },
            ...options
        });
        if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
        if (res.status === 204) return null;
        return await res.json();
    } catch (err) {
        if (err.message.includes('Failed to fetch')) {
            showToast('Backend no disponible. ¿Está corriendo Spring Boot?', 'error');
        } else {
            showToast(err.message, 'error');
        }
        throw err;
    }
}

// ============================================
// TOAST NOTIFICATIONS
// ============================================
function showToast(msg, type = 'info') {
    const c = document.getElementById('toastContainer');
    const t = document.createElement('div');
    t.className = `toast ${type}`;
    t.textContent = msg;
    c.appendChild(t);
    setTimeout(() => { t.style.opacity = '0'; setTimeout(() => t.remove(), 300); }, 3500);
}

// ============================================
// TABS
// ============================================
function switchTab(tabId) {
    document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
    document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
    document.querySelector(`[data-tab="${tabId}"]`).classList.add('active');
    document.getElementById(tabId).classList.add('active');
    if (tabId === 'tab-docentes') loadDocentes();
    if (tabId === 'tab-cursos') loadCursos();
    if (tabId === 'tab-estudiantes') loadEstudiantes();
    if (tabId === 'tab-carnets') loadCarnets();
}

// ============================================
// CHECK BACKEND STATUS
// ============================================
async function checkBackend() {
    const dot = document.getElementById('statusDot');
    const txt = document.getElementById('statusText');
    try {
        await fetch(`${API}/docentes`, { method: 'HEAD' });
        dot.className = 'status-dot online';
        txt.textContent = 'Backend conectado — localhost:8080';
    } catch {
        dot.className = 'status-dot offline';
        txt.textContent = 'Backend no disponible — inicia Spring Boot';
    }
}

// ============================================
// MODALS
// ============================================
function openModal(id) { document.getElementById(id).classList.add('active'); }
function closeModal(id) {
    document.getElementById(id).classList.remove('active');
    const form = document.querySelector(`#${id} form`);
    if (form) form.reset();
    // clear hidden id
    const hid = document.querySelector(`#${id} input[name="id"]`);
    if (hid) hid.value = '';
}

// ============================================
// DOCENTES CRUD
// ============================================
async function loadDocentes() {
    const tbody = document.getElementById('docentesBody');
    tbody.innerHTML = '<tr><td colspan="6"><div class="loading"><div class="spinner"></div></div></td></tr>';
    try {
        const data = await apiFetch('/docentes');
        if (!data || data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6"><div class="empty-state"><div class="icon">👨‍🏫</div><p>No hay docentes registrados</p></div></td></tr>';
            return;
        }
        tbody.innerHTML = data.map(d => `
            <tr>
                <td>${d.id}</td>
                <td><strong>${d.nombre} ${d.apellido}</strong></td>
                <td>${d.email}</td>
                <td>${d.especialidad}</td>
                <td>${d.cursos ? d.cursos.length : 0} cursos</td>
                <td class="actions-cell">
                    <button class="btn btn-teal btn-sm" onclick="editDocente(${d.id})">✏️</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteDocente(${d.id})">🗑️</button>
                </td>
            </tr>
        `).join('');
    } catch { tbody.innerHTML = '<tr><td colspan="6"><div class="empty-state"><p>Error al cargar docentes</p></div></td></tr>'; }
}

async function saveDocente(e) {
    e.preventDefault();
    const form = e.target;
    const id = form.querySelector('[name="id"]').value;
    const body = JSON.stringify({
        nombre: form.nombre.value,
        apellido: form.apellido.value,
        email: form.email.value,
        especialidad: form.especialidad.value
    });
    try {
        if (id) {
            await apiFetch(`/docentes/${id}`, { method: 'PUT', body });
            showToast('Docente actualizado ✅', 'success');
        } else {
            await apiFetch('/docentes', { method: 'POST', body });
            showToast('Docente creado ✅', 'success');
        }
        closeModal('modalDocente');
        loadDocentes();
    } catch {}
}

async function editDocente(id) {
    try {
        const d = await apiFetch(`/docentes/${id}`);
        const form = document.querySelector('#modalDocente form');
        form.querySelector('[name="id"]').value = d.id;
        form.nombre.value = d.nombre;
        form.apellido.value = d.apellido;
        form.email.value = d.email;
        form.especialidad.value = d.especialidad;
        openModal('modalDocente');
    } catch {}
}

async function deleteDocente(id) {
    if (!confirm('¿Eliminar este docente?')) return;
    try {
        await apiFetch(`/docentes/${id}`, { method: 'DELETE' });
        showToast('Docente eliminado 🗑️', 'success');
        loadDocentes();
    } catch {}
}

// ============================================
// CURSOS CRUD
// ============================================
async function loadCursos() {
    const tbody = document.getElementById('cursosBody');
    tbody.innerHTML = '<tr><td colspan="6"><div class="loading"><div class="spinner"></div></div></td></tr>';
    try {
        const data = await apiFetch('/cursos');
        if (!data || data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6"><div class="empty-state"><div class="icon">📚</div><p>No hay cursos registrados</p></div></td></tr>';
            return;
        }
        tbody.innerHTML = data.map(c => `
            <tr>
                <td>${c.id}</td>
                <td><strong>${c.nombre}</strong></td>
                <td>${c.descripcion || '-'}</td>
                <td>${c.creditos}</td>
                <td>${c.docente ? c.docente.nombre + ' ' + c.docente.apellido : 'N/A'}</td>
                <td class="actions-cell">
                    <button class="btn btn-teal btn-sm" onclick="editCurso(${c.id})">✏️</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteCurso(${c.id})">🗑️</button>
                </td>
            </tr>
        `).join('');
    } catch { tbody.innerHTML = '<tr><td colspan="6"><div class="empty-state"><p>Error al cargar cursos</p></div></td></tr>'; }
}

async function loadDocentesSelect() {
    try {
        const data = await apiFetch('/docentes');
        const sel = document.getElementById('cursoDocente');
        sel.innerHTML = '<option value="">Seleccionar docente...</option>' +
            data.map(d => `<option value="${d.id}">${d.nombre} ${d.apellido}</option>`).join('');
    } catch {}
}

async function saveCurso(e) {
    e.preventDefault();
    const form = e.target;
    const id = form.querySelector('[name="id"]').value;
    const docenteId = form.cursoDocente.value;
    const body = JSON.stringify({
        nombre: form.cursoNombre.value,
        descripcion: form.descripcion.value,
        creditos: parseInt(form.creditos.value)
    });
    try {
        if (id) {
            await apiFetch(`/cursos/${id}`, { method: 'PUT', body });
            showToast('Curso actualizado ✅', 'success');
        } else {
            await apiFetch(`/cursos?docenteId=${docenteId}`, { method: 'POST', body });
            showToast('Curso creado ✅', 'success');
        }
        closeModal('modalCurso');
        loadCursos();
    } catch {}
}

async function editCurso(id) {
    try {
        const c = await apiFetch(`/cursos/${id}`);
        await loadDocentesSelect();
        const form = document.querySelector('#modalCurso form');
        form.querySelector('[name="id"]').value = c.id;
        form.cursoNombre.value = c.nombre;
        form.descripcion.value = c.descripcion || '';
        form.creditos.value = c.creditos;
        if (c.docente) form.cursoDocente.value = c.docente.id;
        openModal('modalCurso');
    } catch {}
}

async function deleteCurso(id) {
    if (!confirm('¿Eliminar este curso?')) return;
    try {
        await apiFetch(`/cursos/${id}`, { method: 'DELETE' });
        showToast('Curso eliminado 🗑️', 'success');
        loadCursos();
    } catch {}
}

// ============================================
// ESTUDIANTES CRUD
// ============================================
async function loadEstudiantes() {
    const tbody = document.getElementById('estudiantesBody');
    tbody.innerHTML = '<tr><td colspan="7"><div class="loading"><div class="spinner"></div></div></td></tr>';
    try {
        const data = await apiFetch('/estudiantes');
        if (!data || data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7"><div class="empty-state"><div class="icon">🎓</div><p>No hay estudiantes registrados</p></div></td></tr>';
            return;
        }
        tbody.innerHTML = data.map(e => `
            <tr>
                <td>${e.id}</td>
                <td><strong>${e.nombre} ${e.apellido}</strong></td>
                <td>${e.email}</td>
                <td>${e.fechaNacimiento}</td>
                <td>${e.cursos ? e.cursos.length : 0} cursos</td>
                <td>${e.carnet ? '✅ ' + e.carnet.codigo : '❌'}</td>
                <td class="actions-cell">
                    <button class="btn btn-teal btn-sm" onclick="editEstudiante(${e.id})">✏️</button>
                    <button class="btn btn-danger btn-sm" onclick="deleteEstudiante(${e.id})">🗑️</button>
                </td>
            </tr>
        `).join('');
    } catch { tbody.innerHTML = '<tr><td colspan="7"><div class="empty-state"><p>Error al cargar</p></div></td></tr>'; }
}

async function saveEstudiante(e) {
    e.preventDefault();
    const form = e.target;
    const id = form.querySelector('[name="id"]').value;
    const body = JSON.stringify({
        nombre: form.estNombre.value,
        apellido: form.estApellido.value,
        email: form.estEmail.value,
        fechaNacimiento: form.fechaNacimiento.value
    });
    try {
        if (id) {
            await apiFetch(`/estudiantes/${id}`, { method: 'PUT', body });
            showToast('Estudiante actualizado ✅', 'success');
        } else {
            await apiFetch('/estudiantes', { method: 'POST', body });
            showToast('Estudiante creado ✅', 'success');
        }
        closeModal('modalEstudiante');
        loadEstudiantes();
    } catch {}
}

async function editEstudiante(id) {
    try {
        const e = await apiFetch(`/estudiantes/${id}`);
        const form = document.querySelector('#modalEstudiante form');
        form.querySelector('[name="id"]').value = e.id;
        form.estNombre.value = e.nombre;
        form.estApellido.value = e.apellido;
        form.estEmail.value = e.email;
        form.fechaNacimiento.value = e.fechaNacimiento;
        openModal('modalEstudiante');
    } catch {}
}

async function deleteEstudiante(id) {
    if (!confirm('¿Eliminar este estudiante?')) return;
    try {
        await apiFetch(`/estudiantes/${id}`, { method: 'DELETE' });
        showToast('Estudiante eliminado 🗑️', 'success');
        loadEstudiantes();
    } catch {}
}

// ============================================
// CARNETS CRUD
// ============================================
async function loadCarnets() {
    const tbody = document.getElementById('carnetsBody');
    tbody.innerHTML = '<tr><td colspan="6"><div class="loading"><div class="spinner"></div></div></td></tr>';
    try {
        const data = await apiFetch('/carnets');
        if (!data || data.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6"><div class="empty-state"><div class="icon">🪪</div><p>No hay carnets registrados</p></div></td></tr>';
            return;
        }
        tbody.innerHTML = data.map(c => `
            <tr>
                <td>${c.id}</td>
                <td><strong>${c.codigo}</strong></td>
                <td>${c.fechaEmision}</td>
                <td>${c.fechaExpiracion}</td>
                <td>${c.estudiante ? c.estudiante.nombre + ' ' + c.estudiante.apellido : 'N/A'}</td>
                <td class="actions-cell">
                    <button class="btn btn-danger btn-sm" onclick="deleteCarnet(${c.id})">🗑️</button>
                </td>
            </tr>
        `).join('');
    } catch { tbody.innerHTML = '<tr><td colspan="6"><div class="empty-state"><p>Error al cargar</p></div></td></tr>'; }
}

async function loadEstudiantesSelect() {
    try {
        const data = await apiFetch('/estudiantes');
        const sel = document.getElementById('carnetEstudiante');
        sel.innerHTML = '<option value="">Seleccionar estudiante...</option>' +
            data.map(e => `<option value="${e.id}">${e.nombre} ${e.apellido}</option>`).join('');
    } catch {}
}

async function saveCarnet(e) {
    e.preventDefault();
    const form = e.target;
    const estudianteId = form.carnetEstudiante.value;
    const body = JSON.stringify({
        codigo: form.codigo.value,
        fechaEmision: form.fechaEmision.value,
        fechaExpiracion: form.fechaExpiracion.value
    });
    try {
        await apiFetch(`/carnets?estudianteId=${estudianteId}`, { method: 'POST', body });
        showToast('Carnet creado ✅', 'success');
        closeModal('modalCarnet');
        loadCarnets();
    } catch {}
}

async function deleteCarnet(id) {
    if (!confirm('¿Eliminar este carnet?')) return;
    try {
        await apiFetch(`/carnets/${id}`, { method: 'DELETE' });
        showToast('Carnet eliminado 🗑️', 'success');
        loadCarnets();
    } catch {}
}

// ============================================
// INIT
// ============================================
document.addEventListener('DOMContentLoaded', () => {
    checkBackend();
    setInterval(checkBackend, 15000);
});
