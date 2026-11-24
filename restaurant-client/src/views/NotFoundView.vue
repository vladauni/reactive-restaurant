<template>
  <div class="text-center py-5">
    <h1 class="display-4">404</h1>
    <p class="lead">Такої сторінки не існує</p>
    <router-link to="/" class="btn btn-primary">На головну</router-link>
  </div>
</template>

<script>
export default {
  name: "NotFoundView",
};
</script>
