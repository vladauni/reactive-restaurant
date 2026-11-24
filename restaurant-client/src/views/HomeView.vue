<template>
  <div>
    <h1 class="mb-4">Система «Ресторан»</h1>

    <div v-if="$store.state.error" class="alert alert-danger">
      {{ $store.state.error }}
    </div>

    <div class="row g-3 mb-4">
      <div class="col-md-4">
        <div class="card text-center h-100">
          <div class="card-body">
            <h5 class="card-title">Позицій меню</h5>
            <p class="display-5">{{ MENU_COUNT }}</p>
            <router-link to="/menu" class="btn btn-outline-primary btn-sm"
              >Відкрити</router-link
            >
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card text-center h-100">
          <div class="card-body">
            <h5 class="card-title">Клієнтів</h5>
            <p class="display-5">{{ CLIENTS_COUNT }}</p>
            <router-link to="/clients" class="btn btn-outline-primary btn-sm"
              >Відкрити</router-link
            >
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card text-center h-100">
          <div class="card-body">
            <h5 class="card-title">Замовлень</h5>
            <p class="display-5">{{ ORDERS_COUNT }}</p>
            <router-link to="/orders" class="btn btn-outline-primary btn-sm"
              >Відкрити</router-link
            >
          </div>
        </div>
      </div>
    </div>

    <h4>Замовлення за статусами</h4>
    <ul class="list-group" style="max-width: 400px">
      <li
        v-for="(count, status) in ORDERS_BY_STATUS"
        :key="status"
        class="list-group-item d-flex justify-content-between align-items-center"
      >
        {{ status }}
        <span class="badge bg-primary rounded-pill">{{ count }}</span>
      </li>
      <li v-if="ORDERS_COUNT === 0" class="list-group-item text-muted">
        Замовлень поки немає
      </li>
    </ul>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "HomeView",
  computed: {
    ...mapGetters([
      "CLIENTS_COUNT",
      "MENU_COUNT",
      "ORDERS_COUNT",
      "ORDERS_BY_STATUS",
    ]),
  },
  methods: {
    ...mapActions(["LOAD_ALL"]),
  },
  mounted() {
    this.LOAD_ALL();
  },
};
</script>
