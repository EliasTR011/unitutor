# 🚀 Git Cheat Sheet - Flujo de Trabajo en Equipo

## 📋 Índice
1. [Configuración Inicial](#configuración-inicial)
2. [Flujo Diario Individual](#flujo-diario-individual)
3. [Flujo en Equipo Paralelo](#flujo-en-equipo-paralelo)
4. [Resolución de Conflictos](#resolución-de-conflictos)
5. [Rebase vs Merge](#rebase-vs-merge)
6. [Comandos de Emergencia](#comandos-de-emergencia)
7. [Buenas Prácticas](#buenas-prácticas)

---

## 🔧 Configuración Inicial

### Primera vez en tu máquina:
```bash
# Configurar identidad
git config --global user.name "Tu Nombre"
git config --global user.email "tu@email.com"

# Configurar editor (opcional)
git config --global core.editor "code --wait"

# Configurar push behavior (IMPORTANTE)
git config --global push.default current

# Ver configuración
git config --list
```

### Clonar proyecto:
```bash
# Clonar repositorio
git clone https://gitlab.com/tu-repo/proyecto.git
cd proyecto

# Verificar ramas remotas
git branch -a

# Verificar que main es default
git branch
# Debe mostrar: * main
```

---

## 👤 Flujo Diario Individual

### 1️⃣ Iniciar Nueva Feature

```bash
# SIEMPRE verificar dónde estás
git branch

# Asegurarte de estar en sprint-1 actualizado
git checkout sprint-1
git pull origin sprint-1

# Crear tu feature branch
git checkout -b FEAT-X-nombre-descriptivo
# Ejemplo: git checkout -b FEAT-5-validacion-estudiantes

# Verificar que estás en la nueva rama
git branch
# Debe mostrar: * FEAT-5-validacion-estudiantes
```

### 2️⃣ Trabajar en tu Feature

```bash
# Ver estado actual
git status

# Ver cambios específicos
git diff

# Agregar archivos
git add archivo1.java archivo2.java
# O agregar todo:
git add .

# Commit con mensaje descriptivo
git commit -m "FEAT-5: Add student validation logic"

# IMPORTANTE: Push EXPLÍCITO (primera vez)
git push -u origin FEAT-5-validacion-estudiantes

# Pushes siguientes (después del -u)
git push origin FEAT-5-validacion-estudiantes
# O simplemente: git push (si ya está configurado)
```

### 3️⃣ Commits Frecuentes

```bash
# ✅ BUENA PRÁCTICA: Commits pequeños y frecuentes
git add src/Validador.java
git commit -m "FEAT-5: Add email validation"

git add test/ValidadorTest.java
git commit -m "FEAT-5: Add validation tests"

git push origin FEAT-5-validacion-estudiantes
```

### 4️⃣ Actualizar con Cambios del Sprint

```bash
# Otros mergearon features mientras trabajabas
# Actualizar tu feature con esos cambios:

git checkout FEAT-5-validacion-estudiantes
git fetch origin

# OPCIÓN A: Rebase (RECOMENDADO - historia limpia)
git rebase origin/sprint-1

# OPCIÓN B: Merge (más seguro si hay muchos cambios)
git merge origin/sprint-1

# Push después de rebase (necesita force)
git push --force-with-lease origin FEAT-5-validacion-estudiantes
```

### 5️⃣ Preparar para Mergear

```bash
# Antes de pedir merge, verificar:

# 1. Actualizar con sprint-1
git fetch origin
git rebase origin/sprint-1

# 2. Squash commits (opcional, para limpiar historia)
git log --oneline -10  # Ver cuántos commits tienes
git rebase -i HEAD~5   # Ajusta el número según tus commits

# En el editor:
# pick abc123 FEAT-5: Add validation
# squash def456 FEAT-5: Fix bug      ← Cambiar "pick" por "squash"
# squash ghi789 FEAT-5: Add tests    ← Cambiar "pick" por "squash"

# 3. Push final
git push --force-with-lease origin FEAT-5-validacion-estudiantes

# 4. Crear Pull/Merge Request en GitLab/GitHub
# FROM: FEAT-5-validacion-estudiantes
# TO: sprint-1
# ✅ Enable "Squash commits"
```

### 6️⃣ Después del Merge

```bash
# Tu feature fue mergeada a sprint-1

# 1. Actualizar sprint-1 local
git checkout sprint-1
git pull origin sprint-1

# 2. Borrar feature local
git branch -d FEAT-5-validacion-estudiantes

# 3. Borrar feature remota (opcional)
git push origin --delete FEAT-5-validacion-estudiantes

# 4. Verificar que sprint-1 está limpia
git log --oneline --graph -10
```

---

## 👥 Flujo en Equipo Paralelo

### Escenario: 4 Features en paralelo

```
Tú:      FEAT-1 (validación)
Juan:    FEAT-2 (registro)
María:   FEAT-3 (login)
Pedro:   FEAT-4 (dashboard)
```

### Cada uno trabaja independiente:

```bash
# TÚ (en FEAT-1):
git checkout sprint-1
git pull origin sprint-1
git checkout -b FEAT-1-validacion
# ... trabajo ...
git commit -m "FEAT-1: Add validation"
git push origin FEAT-1-validacion
```

```bash
# JUAN (en FEAT-2):
git checkout sprint-1
git pull origin sprint-1
git checkout -b FEAT-2-registro
# ... trabajo ...
git commit -m "FEAT-2: Add registration"
git push origin FEAT-2-registro
```

### Orden de merge (IMPORTANTE):

```bash
# 1. FEAT-2 se completa primero
# Juan hace PR → sprint-1
# Se aprueba y mergea

# 2. Tú necesitas actualizar FEAT-1 con FEAT-2:
git checkout FEAT-1-validacion
git fetch origin
git rebase origin/sprint-1  # Trae cambios de FEAT-2
git push --force-with-lease origin FEAT-1-validacion

# 3. FEAT-1 ahora puede mergearse sin conflictos
# Haces PR → sprint-1

# 4. María actualiza FEAT-3 con FEAT-1 y FEAT-2:
git checkout FEAT-3-login
git fetch origin
git rebase origin/sprint-1
# ... etc
```

### Comunicación del Equipo:

```bash
# ✅ ANTES de mergear feature importante, avisar al equipo:
"Voy a mergear FEAT-2 (registro) a sprint-1. 
Después del merge, actualicen sus features."

# ✅ DESPUÉS del merge:
"FEAT-2 mergeada. Ejecuten:
git fetch origin
git rebase origin/sprint-1"
```

---

## 🔥 Resolución de Conflictos

### Conflicto durante Rebase:

```bash
git rebase origin/sprint-1

# ❌ Error: CONFLICT in src/Usuario.java
# Estado: (FEAT-1|REBASE 1/3)

# 1. Ver archivos en conflicto
git status

# 2. Abrir archivo conflictivo
# Verás algo como:
<<<<<<< HEAD (tu código)
public String nombre;
=======
public String nombreCompleto;  (código de sprint-1)
>>>>>>> abc123

# 3. Editar manualmente, decidir qué mantener:
public String nombreCompleto;  # Mantener remoto
# O combinar:
public String nombre;
public String nombreCompleto;

# 4. ELIMINAR marcadores de conflicto:
# Borrar: <<<<<<< HEAD
# Borrar: =======
# Borrar: >>>>>>> abc123

# 5. Marcar como resuelto
git add src/Usuario.java

# 6. Continuar rebase
git rebase --continue

# 7. Si hay más conflictos, repetir pasos 2-6
# Si todo está bien:
git push --force-with-lease origin FEAT-1-validacion
```

### Conflicto durante Merge:

```bash
git merge origin/sprint-1

# ❌ Error: CONFLICT in src/Usuario.java
# Estado: (FEAT-1|MERGING)

# 1-4. Mismo proceso de resolución que rebase

# 5. Marcar como resuelto
git add src/Usuario.java

# 6. Completar merge
git commit -m "Resolve merge conflict in Usuario.java"

# 7. Push
git push origin FEAT-1-validacion
```

### Abortar si algo sale mal:

```bash
# Durante rebase:
git rebase --abort

# Durante merge:
git merge --abort

# Vuelves al estado anterior ✅
```

### Conflictos complejos - Estrategia:

```bash
# Si el conflicto es muy complejo:

# 1. Abortar
git rebase --abort

# 2. Hacer merge en lugar de rebase (más seguro)
git merge origin/sprint-1

# 3. O pedir ayuda al autor del código conflictivo
"Hey Juan, FEAT-2 y FEAT-1 tienen conflictos en Usuario.java.
¿Podemos revisarlo juntos?"
```

---

## 🔄 Rebase vs Merge

### ¿Cuándo usar Rebase?

✅ **Usar REBASE cuando:**
- Actualizas tu feature con sprint-1
- Quieres historia lineal y limpia
- Trabajas solo en la rama
- Antes de hacer PR

```bash
# Ejemplo: Actualizar FEAT-1 con cambios de sprint-1
git checkout FEAT-1
git rebase origin/sprint-1  # ✅ Historia limpia

# Resultado:
sprint-1 ─●─●─●
               \
                ●─●─● FEAT-1 (actualizada)
```

### ¿Cuándo usar Merge?

✅ **Usar MERGE cuando:**
- Integras feature completa a sprint-1
- Varios trabajan en la misma rama
- Quieres preservar historia completa
- Conflictos muy complejos

```bash
# Ejemplo: Mergear FEAT-1 completa a sprint-1
git checkout sprint-1
git merge --squash FEAT-1  # ✅ Preserva historia
git commit -m "FEAT-1: Add validation feature"

# Resultado:
sprint-1 ─●─●─●─────●
               \    /
                ●─●  FEAT-1
```

### Tabla Comparativa:

| Aspecto | Rebase | Merge |
|---------|--------|-------|
| Historia | Lineal | Con bifurcaciones |
| Conflictos | Uno por uno | Todos juntos |
| Seguridad | Reescribe historia | No reescribe |
| Uso | Actualizar features | Integrar features |
| Push después | `--force-with-lease` | Normal |

### Comandos Rebase:

```bash
# Rebase básico
git rebase origin/sprint-1

# Rebase interactivo (para limpiar commits)
git rebase -i HEAD~5

# Continuar después de resolver conflicto
git rebase --continue

# Saltar commit problemático
git rebase --skip

# Abortar rebase
git rebase --abort
```

### Comandos Merge:

```bash
# Merge normal
git merge origin/sprint-1

# Merge con squash (recomendado para features)
git merge --squash FEAT-1

# Merge sin fast-forward (crea merge commit)
git merge --no-ff FEAT-1

# Abortar merge
git merge --abort
```

---

## 🆘 Comandos de Emergencia

### "Hice commit en la rama equivocada"

```bash
# Estabas en sprint-1 pero debías estar en FEAT-1

# 1. Ver el commit que hiciste
git log --oneline -5

# 2. Copiar el hash del commit (ej: abc123)

# 3. Crear feature con ese commit
git checkout -b FEAT-1-rescate

# 4. Volver sprint-1 al estado anterior
git checkout sprint-1
git reset --hard origin/sprint-1

# ✅ Tu commit ahora está en FEAT-1-rescate
```

### "Pusheé a la rama equivocada"

```bash
# Pusheaste FEAT-1 a sprint-1 por error

# 1. Si nadie más ha pulleado (inmediato):
git push --force origin sprint-1:sprint-1-backup  # Backup
git checkout sprint-1
git reset --hard origin/main
git push --force origin sprint-1

# 2. Si otros ya pullearon (contacta al equipo):
"Cometí un error, no hagan pull de sprint-1 aún"
```

### "Tengo cambios sin guardar y necesito cambiar de rama"

```bash
# Opción 1: Stash (guardar temporalmente)
git stash
git checkout otra-rama
# ... trabajo ...
git checkout FEAT-1
git stash pop  # Recuperar cambios

# Opción 2: Commit temporal
git add .
git commit -m "WIP: trabajo en progreso"
git checkout otra-rama
# ... trabajo ...
git checkout FEAT-1
git reset HEAD~1  # Deshacer último commit, mantener cambios
```

### "Quiero deshacer el último commit"

```bash
# Deshacer commit pero mantener cambios
git reset --soft HEAD~1

# Deshacer commit y cambios (CUIDADO)
git reset --hard HEAD~1

# Deshacer commit que ya pusheaste
git revert HEAD
git push origin FEAT-1
```

### "Mi rama está totalmente rota"

```bash
# Resetear a estado limpio del remoto
git fetch origin
git reset --hard origin/FEAT-1

# O empezar de cero
git checkout sprint-1
git branch -D FEAT-1  # Forzar borrado
git checkout -b FEAT-1
```

### "Borré algo importante"

```bash
# Ver historial de dónde estuvo HEAD
git reflog

# Verás:
# abc123 HEAD@{0}: reset: moving to origin/sprint-1
# def456 HEAD@{1}: commit: Add important feature  ← Aquí estaba

# Recuperar
git reset --hard def456
```

### "Necesito código de otro branch sin mergear todo"

```bash
# Cherry-pick (copiar commit específico)
git checkout FEAT-1
git log --oneline  # Encontrar hash del commit que quieres

git checkout FEAT-2
git cherry-pick abc123  # Copia ese commit a FEAT-2
```

---

## ✨ Buenas Prácticas

### 📝 Mensajes de Commit

```bash
# ✅ BUENOS mensajes:
git commit -m "FEAT-5: Add email validation logic"
git commit -m "FEAT-5: Fix null pointer in validator"
git commit -m "FEAT-5: Add unit tests for validation"

# ❌ MALOS mensajes:
git commit -m "fix"
git commit -m "changes"
git commit -m "asdasd"
git commit -m "final version 3"
```

**Formato recomendado:**
```
FEAT-X: Verbo en infinitivo + descripción breve

Ejemplos:
FEAT-1: Add student registration form
FEAT-2: Fix validation error message
FEAT-3: Update database schema
FEAT-4: Remove deprecated methods
```

### 🔍 Antes de Cada Commit

```bash
# Checklist:
✅ git status          # ¿Qué cambié?
✅ git diff            # ¿Los cambios son correctos?
✅ git branch          # ¿Estoy en MI feature?
✅ git add específico  # Solo lo necesario
✅ git commit -m "..."  # Mensaje claro
✅ git push origin FEAT-X  # Push EXPLÍCITO
```

### 🚫 NUNCA Hacer

```bash
# ❌ NUNCA trabajar directo en sprint-1 o main
git checkout sprint-1
git commit -m "..."  # ❌ MAL

# ❌ NUNCA push genérico sin especificar
git push  # ❌ Peligroso si upstream mal configurado

# ❌ NUNCA force push sin --force-with-lease
git push --force  # ❌ Puede borrar trabajo de otros

# ❌ NUNCA commitear archivos generados
git add target/  # ❌ Binarios compilados
git add .idea/   # ❌ Configuración IDE
git add *.class  # ❌ Archivos compilados

# ✅ Usar .gitignore en su lugar
```

### 📁 .gitignore esencial

```bash
# Crear/editar .gitignore en raíz del proyecto:
# Java
*.class
*.jar
*.war
target/
out/

# IDEs
.idea/
.vscode/
*.iml
.DS_Store

# Logs
*.log

# Base de datos local
*.db
*.sqlite
```

### 🔄 Flujo de Trabajo Ideal

```bash
# Día 1: Crear feature
git checkout sprint-1
git pull origin sprint-1
git checkout -b FEAT-X
# ... trabajo ...
git commit -m "FEAT-X: Initial implementation"
git push -u origin FEAT-X

# Día 2: Continuar
git checkout FEAT-X
# ... trabajo ...
git commit -m "FEAT-X: Add tests"
git push origin FEAT-X

# Día 3: Actualizar con sprint-1
git fetch origin
git rebase origin/sprint-1
git push --force-with-lease origin FEAT-X

# Día 4: Feature completa
git rebase -i HEAD~5  # Limpiar commits
git push --force-with-lease origin FEAT-X
# Crear PR en GitLab/GitHub

# Después del merge:
git checkout sprint-1
git pull origin sprint-1
git branch -d FEAT-X
```

### 🎯 Comunicación en Equipo

```bash
# ✅ Avisar antes de mergear feature grande
"Voy a mergear FEAT-2 (cambios en modelo de datos) a sprint-1 en 5 min"

# ✅ Avisar después de merge
"FEAT-2 mergeada ✅. Actualicen sus features con: git rebase origin/sprint-1"

# ✅ Reportar problemas
"Tengo conflictos entre FEAT-1 y FEAT-3 en Usuario.java. ¿Revisamos juntos?"

# ✅ Hacer code review
"Revisen mi PR de FEAT-4 cuando puedan 👀"
```

### 📊 Verificar Estado del Proyecto

```bash
# Ver todas las ramas
git branch -a

# Ver ramas mergeadas
git branch --merged sprint-1

# Ver ramas NO mergeadas
git branch --no-merged sprint-1

# Ver quién cambió qué
git log --oneline --author="Juan" -10

# Ver cambios en archivo específico
git log --oneline -- src/Usuario.java

# Ver diferencias entre ramas
git diff sprint-1..FEAT-1

# Ver commits que FEAT-1 tiene que sprint-1 no
git log sprint-1..FEAT-1 --oneline
```

### 🧹 Limpieza Periódica

```bash
# Cada semana o después de varios merges:

# 1. Actualizar info de ramas remotas
git fetch --prune

# 2. Ver ramas ya mergeadas
git branch --merged sprint-1

# 3. Borrar ramas locales mergeadas
git branch -d FEAT-1 FEAT-2 FEAT-3

# 4. Ver ramas remotas borradas pero aún en local
git remote prune origin --dry-run

# 5. Limpiar referencias remotas obsoletas
git remote prune origin
```

---

## 🎓 Resumen Ultra-Rápido

### Crear feature:
```bash
git checkout sprint-1 && git pull && git checkout -b FEAT-X
```

### Trabajar:
```bash
git add . && git commit -m "FEAT-X: description" && git push origin FEAT-X
```

### Actualizar:
```bash
git fetch origin && git rebase origin/sprint-1 && git push --force-with-lease origin FEAT-X
```

### Después del merge:
```bash
git checkout sprint-1 && git pull && git branch -d FEAT-X
```

### En conflicto:
```bash
# Resolver archivo → git add archivo → git rebase --continue
# O: git rebase --abort
```

---

## 🆘 Ayuda Rápida

```bash
# ¿En qué rama estoy?
git branch

# ¿Qué cambié?
git status

# ¿Qué hice últimamente?
git log --oneline -10

# ¡Arruiné todo!
git reset --hard origin/MI-RAMA

# ¿Cómo salgo de esto?
git rebase --abort  # o git merge --abort

# ¿Dónde está mi código perdido?
git reflog
```

---

## 📚 Comandos por Categoría

### Información:
```bash
git status              # Estado actual
git log --oneline       # Historial
git log --graph         # Historial gráfico
git branch              # Ramas locales
git branch -a           # Todas las ramas
git diff                # Cambios sin staged
git diff --staged       # Cambios staged
git reflog              # Historial de HEAD
```

### Navegar:
```bash
git checkout rama       # Cambiar de rama
git checkout -b nueva   # Crear y cambiar
git switch rama         # Cambiar (nuevo)
git switch -c nueva     # Crear y cambiar (nuevo)
```

### Actualizar:
```bash
git fetch origin        # Traer info remota
git pull origin rama    # Traer y mergear
git pull --rebase       # Traer y rebasear
```

### Guardar cambios:
```bash
git add archivo         # Agregar específico
git add .               # Agregar todo
git commit -m "msg"     # Commitear
git commit --amend      # Editar último commit
git push origin rama    # Subir cambios
```

### Deshacer:
```bash
git reset --soft HEAD~1 # Deshacer commit, mantener cambios
git reset --hard HEAD~1 # Deshacer commit y cambios
git revert HEAD         # Crear commit inverso
git checkout -- file    # Descartar cambios en archivo
git restore file        # Descartar cambios (nuevo)
```

### Limpiar:
```bash
git clean -n            # Ver qué se borraría
git clean -fd           # Borrar archivos no tracked
git branch -d rama      # Borrar rama mergeada
git branch -D rama      # Forzar borrado
```

---

**💡 Tip Final:** Imprime este cheat sheet y tenlo a mano. Con el tiempo, estos comandos se volverán segunda naturaleza. ¡Buena suerte! 🚀