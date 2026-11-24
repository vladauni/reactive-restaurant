<template>
  <div>
    <h1 class="mb-3">Клієнти</h1>
    <div v-if="error" class="alert alert-danger">{{ error }}</div>

    <div class="row">
      <div class="col-lg-4 mb-4">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">
              {{ form.id ? "Редагування клієнта" : "Новий клієнт" }}
            </h5>
            <div class="mb-3">
              <label for="firstName" class="form-label">Ім'я</label>
              <input
                id="firstName"
                type="text"
                class="form-control"
                v-model="form.firstName"
              />
            </div>
            <div class="mb-3">
              <label for="lastName" class="form-label">Прізвище</label>
              <input
                id="lastName"
                type="text"
                class="form-control"
                v-model="form.lastName"
              />
            </div>
            <div class="mb-3">
              <label for="phone" class="form-label">Телефон</label>
              <input
                id="phone"
                type="text"
                class="form-control"
                v-model="form.phone"
                placeholder="+380..."
              />
            </div>
            <button class="btn btn-success me-2" @click="saveClient">
              {{ form.id ? "Зберегти" : "Додати" }}
            </button>
            <button v-if="form.id" class="btn btn-secondary" @click="resetForm">
              Скасувати
            </button>
          </div>
        </div>
      </div>

      <div class="col-lg-8">
        <table class="table table-striped align-middle">
          <thead>
            <tr>
              <th>ID</th>
              <th>Ім'я</th>
              <th>Прізвище</th>
              <th>Телефон</th>
              <th style="width: 190px"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="c in clients" :key="c.id">
              <td>{{ c.id }}</td>
              <td>{{ c.firstName }}</td>
              <td>{{ c.lastName }}</td>
              <td>{{ c.phone }}</td>
              <td class="text-end">
                <button
                  class="btn btn-outline-primary btn-sm me-2"
                  @click="editClient(c)"
                >
                  Редагувати
                </button>
                <button
                  class="btn btn-outline-danger btn-sm"
                  @click="deleteClient(c)"
                >
                  Видалити
                </button>
              </td>
            </tr>
            <tr v-if="clients.length === 0">
              <td colspan="5" class="text-center text-muted">Клієнтів немає</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import ClientService from "@/service/ClientService";

const emptyForm = () => ({ id: null, firstName: "", lastName: "", phone: "" });

export default {
  name: "ClientsView",
  data() {
    return { clients: [], form: emptyForm(), error: "" };
  },
  methods: {
    getAll() {
      ClientService.findAll()
        .then((response) => {
          this.clients = response.data;
          this.error = "";
        })
        .catch(() => {
          this.error = "Не вдалося завантажити клієнтів";
        });
    },
    saveClient() {
      if (!this.form.firstName || !this.form.lastName) {
        this.error = "Вкажіть ім'я та прізвище";
        return;
      }
      const data = {
        firstName: this.form.firstName,
        lastName: this.form.lastName,
        phone: this.form.phone,
      };
      const request = this.form.id
        ? ClientService.update(this.form.id, data)
        : ClientService.save(data);
      request
        .then(() => {
          this.resetForm();
          this.getAll();
        })
        .catch(() => {
          this.error = "Не вдалося зберегти клієнта";
        });
    },
    editClient(c) {
      this.form = {
        id: c.id,
        firstName: c.firstName,
        lastName: c.lastName,
        phone: c.phone,
      };
    },
    deleteClient(c) {
      if (!confirm(`Видалити клієнта ${c.firstName} ${c.lastName}?`)) return;
      ClientService.delete(c.id)
        .then(() => this.getAll())
        .catch(() => {
          this.error = "Не вдалося видалити (у клієнта можуть бути замовлення)";
        });
    },
    resetForm() {
      this.form = emptyForm();
      this.error = "";
    },
  },
  mounted() {
    this.getAll();
  },
};
</script>
