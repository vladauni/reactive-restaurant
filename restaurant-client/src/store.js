import { createStore } from "vuex";
import ClientService from "@/service/ClientService";
import MenuItemService from "@/service/MenuItemService";
import OrderService from "@/service/OrderService";

const store = createStore({
  state: { clients: [], menuItems: [], orders: [], error: "" },
  mutations: {
    SET_CLIENTS: (state, clients) => {
      state.clients = clients;
    },
    SET_MENU_ITEMS: (state, items) => {
      state.menuItems = items;
    },
    SET_ORDERS: (state, orders) => {
      state.orders = orders;
    },
    SET_ERROR: (state, message) => {
      state.error = message;
    },
  },
  actions: {
    async LOAD_ALL({ commit }) {
      try {
        const [clients, menu, orders] = await Promise.all([
          ClientService.findAll(),
          MenuItemService.findAll(),
          OrderService.findAll(),
        ]);
        commit("SET_CLIENTS", clients.data);
        commit("SET_MENU_ITEMS", menu.data);
        commit("SET_ORDERS", orders.data);
        commit("SET_ERROR", "");
      } catch (e) {
        commit(
          "SET_ERROR",
          "Не вдалося отримати дані з сервера (перевірте, що він запущений на порту 9090)"
        );
      }
    },
  },
  getters: {
    CLIENTS_COUNT: (state) => state.clients.length,
    MENU_COUNT: (state) => state.menuItems.length,
    ORDERS_COUNT: (state) => state.orders.length,
    ORDERS_BY_STATUS: (state) => {
      const result = {};
      state.orders.forEach((o) => {
        result[o.status] = (result[o.status] || 0) + 1;
      });
      return result;
    },
  },
});

export default store;
