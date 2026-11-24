<template>
  <div>
    <h1 class="mb-3">Замовлення</h1>
    <div v-if="error" class="alert alert-danger">{{ error }}</div>

    <div class="row">
      <div class="col-lg-4 mb-4">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">Нове замовлення</h5>
            <div class="mb-3">
              <label for="client" class="form-label">Клієнт</label>
              <select
                id="client"
                class="form-select"
                v-model="newOrder.clientId"
              >
                <option :value="null" disabled>— оберіть клієнта —</option>
                <option v-for="c in clients" :key="c.id" :value="c.id">
                  {{ c.firstName }} {{ c.lastName }}
                </option>
              </select>
            </div>
            <div class="mb-3">
              <label for="status" class="form-label">Статус</label>
              <select id="status" class="form-select" v-model="newOrder.status">
                <option v-for="s in statuses" :key="s" :value="s">
                  {{ s }}
                </option>
              </select>
            </div>
            <button class="btn btn-success" @click="createOrder">
              Створити
            </button>
          </div>
        </div>
      </div>

      <div class="col-lg-8">
        <table class="table table-striped align-middle">
          <thead>
            <tr>
              <th>ID</th>
              <th>Клієнт</th>
              <th>Адмін (ID)</th>
              <th>Статус</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="o in orders" :key="o.id">
              <td>{{ o.id }}</td>
              <td>{{ clientName(o.clientId) }}</td>
              <td>{{ o.administratorId || "—" }}</td>
              <td>
                <select
                  class="form-select form-select-sm"
                  :value="o.status"
                  @change="changeStatus(o, $event.target.value)"
                >
                  <option v-for="s in statuses" :key="s" :value="s">
                    {{ s }}
                  </option>
                </select>
              </td>
              <td class="text-end">
                <button
                  class="btn btn-outline-danger btn-sm"
                  @click="deleteOrder(o)"
                >
                  Видалити
                </button>
              </td>
            </tr>
            <tr v-if="orders.length === 0">
              <td colspan="5" class="text-center text-muted">
                Замовлень немає
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import OrderService from "@/service/OrderService";
import ClientService from "@/service/ClientService";

export default {
  name: "OrdersView",
  data() {
    return {
      orders: [],
      clients: [],
      statuses: ["NEW", "CONFIRMED", "IN_KITCHEN", "READY", "BILLED", "PAID"],
      newOrder: { clientId: null, status: "NEW" },
      error: "",
    };
  },
  methods: {
    getAll() {
      Promise.all([OrderService.findAll(), ClientService.findAll()])
        .then(([orders, clients]) => {
          this.orders = orders.data;
          this.clients = clients.data;
          this.error = "";
        })
        .catch(() => {
          this.error = "Не вдалося завантажити дані";
        });
    },
    clientName(id) {
      const c = this.clients.find((x) => x.id === id);
      return c ? `${c.firstName} ${c.lastName}` : `#${id}`;
    },
    createOrder() {
      if (!this.newOrder.clientId) {
        this.error = "Оберіть клієнта";
        return;
      }
      OrderService.save({
        clientId: this.newOrder.clientId,
        status: this.newOrder.status,
      })
        .then(() => {
          this.newOrder = { clientId: null, status: "NEW" };
          this.getAll();
        })
        .catch(() => {
          this.error = "Не вдалося створити замовлення";
        });
    },
    changeStatus(order, status) {
      OrderService.update(order.id, {
        clientId: order.clientId,
        administratorId: order.administratorId,
        status: status,
      })
        .then(() => this.getAll())
        .catch(() => {
          this.error = "Не вдалося змінити статус";
        });
    },
    deleteOrder(order) {
      if (!confirm(`Видалити замовлення №${order.id}?`)) return;
      OrderService.delete(order.id)
        .then(() => this.getAll())
        .catch(() => {
          this.error =
            "Не вдалося видалити (до замовлення можуть бути прив'язані позиції чи рахунок)";
        });
    },
  },
  mounted() {
    this.getAll();
  },
};
</script>
