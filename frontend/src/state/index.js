import { action, createStore } from "easy-peasy";

const store = createStore({
  chats: {
    items: [],
    add: action((state, payload) => {
      state.items.push(payload);
    }),
  },
});

export default store