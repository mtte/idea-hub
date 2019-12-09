<template>
  <v-card
    light
  >
    <v-card-title class="headline">
      Unlimited music now
    </v-card-title>

    <v-card-text>
      <vue-markdown
        :source="mdText"
      />
    </v-card-text>

    <!--    <v-card-actions>-->
    <!--      <v-btn text>-->
    <!--        Listen Now-->
    <!--      </v-btn>-->
    <!--    </v-card-actions>-->
  </v-card>
</template>

<script>
import VueMarkdown from 'vue-markdown'

export default {
  name: 'NoteCard',
  components: {
    VueMarkdown
  },
  data: function () {
    return {
      mdText:
        '## Typographic replacements\n' +
        '\n' +
        'Enable typographer option to see result.\n' +
        '\n' +
        '(c) (C) (r) (R) (tm) (TM) (p) (P) +-\n' +
        '\n' +
        'test.. test... test..... test?..... test!....\n' +
        '\n' +
        '!!!!!! ???? ,,  -- ---\n' +
        '\n' +
        '"Smartypants, double quotes" and \'single quotes\'\n' +
        '\n' +
        '\n' +
        '## Emphasis\n' +
        '\n' +
        '**This is bold text**\n' +
        '\n' +
        '__This is bold text__\n' +
        '\n' +
        '*This is italic text*\n' +
        '\n' +
        '_This is italic text_\n' +
        '\n' +
        '~~Strikethrough~~\n' +
        '\n' +
        '\n' +
        '## Blockquotes\n' +
        '\n' +
        '\n' +
        '> Blockquotes can also be nested...\n' +
        '>> ...by using additional greater-than signs right next to each other...\n' +
        '> > > ...or with spaces between arrows.\n' +
        '\n' +
        '\n' +
        '## Lists\n' +
        '\n' +
        'Unordered\n' +
        '\n' +
        '+ Create a list by starting a line with `+`, `-`, or `*`\n' +
        '+ Sub-lists are made by indenting 2 spaces:\n' +
        '  - Marker character change forces new list start:\n' +
        '    * Ac tristique libero volutpat at\n' +
        '    + Facilisis in pretium nisl aliquet\n' +
        '    - Nulla volutpat aliquam velit\n' +
        '+ Very easy!\n' +
        '\n' +
        'Ordered\n' +
        '\n' +
        '1. Lorem ipsum dolor sit amet\n' +
        '2. Consectetur adipiscing elit\n' +
        '3. Integer molestie lorem at massa\n' +
        '\n' +
        '\n' +
        '1. You can use sequential numbers...\n' +
        '1. ...or keep all the numbers as `1.`\n' +
        '\n' +
        'Start numbering with offset:\n' +
        '\n' +
        '57. foo\n'
    }
  }
}
</script>

<style scoped>

</style>
