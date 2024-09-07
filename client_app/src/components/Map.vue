<template>
  <div style="width: 500px; height: 500px"></div>
</template>

<script lang="ts">
import DG from "2gis-maps";

export default {
  data: () => ({
    map: null,
  }),
  mounted() {
    this.map = new DG.map(this.$el, {
      center: [59.972828, 30.323714],
      zoom: 16,
    });
    const marker = DG.marker([59.972828, 30.323714]).addTo(this.map);
    const popupContent =
      "Санкт-Петербургский Государственный электротехнический университет ЛЭТИ им. В.И. Ульянова (Ленина)";
    marker.bindPopup(popupContent);
  },
  beforeDestroy() {
    if (this.map) this.map.remove();
  },
};
</script>