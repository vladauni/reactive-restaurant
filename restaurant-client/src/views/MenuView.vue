<template>
  <div>
    <h1 class="mb-3">Меню</h1>
    <div v-if="error" class="alert alert-danger">{{ error }}</div>

    <div class="row">
      <div class="col-lg-4 mb-4">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">
              {{ form.id ? "Редагування позиції" : "Нова позиція" }}
            </h5>
            <div class="mb-3">
              <label for="name" class="form-label">Назва</label>
              <input
                id="name"
                type="text"
                class="form-control"
                v-model="form.name"
              />
            </div>
            <div class="mb-3">
              <label for="price" class="form-label">Ціна, грн</label>
              <input
                id="price"
                type="number"
                min="0"
                step="0.01"
                class="form-control"
                v-model.number="form.price"
              />
            </div>
            <button class="btn btn-success me-2" @click="saveItem">
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
              <th>Назва</th>
              <th>Ціна, грн</th>
              <th style="width: 190px"></th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.name }}</td>
              <td>{{ Number(item.price).toFixed(2) }}</td>
              <td class="text-end">
                <button
                  class="btn btn-outline-primary btn-sm me-2"
                  @click="editItem(item)"
                >
                  Редагувати
                </button>
                <button
                  class="btn btn-outline-danger btn-sm"
                  @click="deleteItem(item)"
                >
                  Видалити
                </button>
              </td>
            </tr>
            <tr v-if="items.length === 0">
              <td colspan="4" class="text-center text-muted">Позицій немає</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import MenuItemService from "@/service/MenuItemService";

export default {
  name: "MenuView",
  data() {
    return {
      items: [],
      form: { id: null, name: "", price: 0 },
      error: "",
    };
  },
  methods: {
    getAll() {
      MenuItemService.findAll()
        .then((response) => {
          this.items = response.data;
          this.error = "";
        })
        .catch(() => {
          this.error = "Не вдалося завантажити меню";
        });
    },
    saveItem() {
      if (!this.form.name || this.form.price === "" || this.form.price < 0) {
        this.error = "Вкажіть назву та коректну ціну";
        return;
      }
      const data = { name: this.form.name, price: this.form.price };
      const request = this.form.id
        ? MenuItemService.update(this.form.id, data)
        : MenuItemService.save(data);
      request
        .then(() => {
          this.resetForm();
          this.getAll();
        })
        .catch(() => {
          this.error = "Не вдалося зберегти позицію";
        });
    },
    editItem(item) {
      this.form = { id: item.id, name: item.name, price: item.price };
    },
    deleteItem(item) {
      if (!confirm(`Видалити «${item.name}»?`)) return;
      MenuItemService.delete(item.id)
        .then(() => this.getAll())
        .catch(() => {
          this.error = "Не вдалося видалити (позиція може бути в замовленнях)";
        });
    },
    resetForm() {
      this.form = { id: null, name: "", price: 0 };
      this.error = "";
    },
  },
  mounted() {
    this.getAll();
  },
};
</script>
