<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
      <router-link class="navbar-brand" to="/">Ресторан</router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#mainMenu"
        aria-controls="mainMenu"
        aria-expanded="false"
        aria-label="Меню"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="mainMenu">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link class="nav-link" to="/" exact-active-class="active"
              >Головна</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/menu" active-class="active"
              >Меню</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/clients" active-class="active"
              >Клієнти</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/orders" active-class="active"
              >Замовлення</router-link
            >
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <main class="container py-4">
    <router-view />
  </main>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
