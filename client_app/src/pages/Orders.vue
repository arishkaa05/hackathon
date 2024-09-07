<template>
  <div>
    <div
      class="liner-container mb-6 mt-5 flex justify-center border-b-2 border-gray"
    >
      <h1
        class="mb-[-2px] inline-block border-b-2 border-[#02346f] pb-3 text-2xl font-bold uppercase"
      >
        Заказы
      </h1>
    </div>
    <div class="card w-full bg-white shadow-xl">
      <div class="card-body">
        <div class="my-20" v-if="!orders[0]">
          <h3 class="text-center mb-3 text-xl text-[#02346f] font-bold">
            Вы еще ничего не заказали
          </h3>
          <div class="flex justify-center">
            <a
              @click.stop="$router.push('/category')"
              class="text-3xl cursor-pointer hover:text-[#bb8d54] text-[#02346f] font-bold"
              >За покупками!</a
            >
          </div>
        </div>
        <div v-if="orders && orders[0]">
          <div class="w-full" v-for="order in orders" :key="order.id">
            <div class="collapse mb-6 collapse-arrow bg-base-200">
              <input type="radio" name="my-accordion-2" checked="checked" /> 
              <div class="collapse-title text-xl font-medium">
                <div class="text-sm text-gray 500 flex w-full   justify-between">
                  <div>{{formattedDate(order.createDate)}}</div>
                  <div> <div class="badge badge-accent p-3">{{ order.status }}</div></div>
                </div>
              </div>
              <div class="collapse-content"> 
                <hr>
                <ul class="pl-5 mt-3">
                  <li class="list-disc mb-3" v-for="product in order.products" :key=product.id> 
                   <a class="cursor-pointer flex justify-between hover:text-[#bb8d54]" @click.stop="$router.push(`/product/${product.titleForUrl}`)"> {{product.title}} <h2 class="text-xl"></h2> <h2 class="text-xl font-semibold"> {{product.price}}₽</h2></a>

                  </li>
                </ul>
              </div>
            </div>      
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { getOrdert } from "../hooks/useOrder";

const isHovered = ref(false);
const currentPage = ref(1);


const formattedDate = (createdDate: string) => {
  const date = new Date(createdDate);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');

      return `${day}.${month}.${year}`;
};
let orders = ref([]);

onMounted(async () => {
  const response = await getOrdert();
  orders.value = { ...response };
  console.log(response);
});
</script>



<style scoped>
</style>