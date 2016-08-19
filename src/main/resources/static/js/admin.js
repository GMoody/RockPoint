$(document).ready(function ($) {
    
    var table = $('#tableSorting').stupidtable();
    $('#js-page-content table:first-child').addClass('u--boss');
    clearTheCompareIterms();
    scrollEvents();
    fillterContent();
    compareItems();

        var opts = {
        lines: 17 // The number of lines to draw
        , length: 17 // The length of each line
        , width: 3 // The line thickness
        , radius: 10 // The radius of the inner circle
        , scale: 1 // Scales overall size of the spinner
        , corners: 1 // Corner roundness (0..1)
        , color: '#000' // #rgb or #rrggbb or array of colors
        , opacity: 0 // Opacity of the lines
        , rotate: 0 // The rotation offset
        , direction: 1 // 1: clockwise, -1: counterclockwise
        , speed: 1.3 // Rounds per second
        , trail: 63 // Afterglow percentage
        , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
        , zIndex: 2e9 // The z-index (defaults to 2000000000)
        , className: 'spinner' // The CSS class to assign to the spinner
        , top: '50%' // Top position relative to parent
        , left: '50%' // Left position relative to parent
        , shadow: false // Whether to render a shadow
        , hwaccel: false // Whether to use hardware acceleration
        , position: 'absolute' // Element positioning
    }


        table.on("beforetablesort", function (event, data) {
          $('.u-loading--white').show();
          var target = document.getElementById('load')
          var spinner = new Spinner(opts).spin(target);
        });
        table.on("aftertablesort", function (event, data) {
          $('.u-loading--white').hide();
        });



    $('body').on('click', 'th', function(event) {
        event.preventDefault();
        if($(this).hasClass('sorting-asc'))
            $(this).children('i').text('arrow_drop_up');
        else
            $(this).children('i').text('arrow_drop_down');
    });
      
    $.fn.scrollStopped = function(callback) {
      var that = this, $this = $(that);
      $this.scroll(function(ev) {
        clearTimeout($this.data('scrollTimeout'));
        $this.data('scrollTimeout', setTimeout(callback.bind(that), 250, ev));
      });
    };

    $('.u--boss').scrollStopped(function(ev){
      $('#js-thead-helper').css('visibility', 'visible');
    });
    
    $('#js-tbody-helper > tr:first-child').appendTo('#tableSorting thead');
    $('#js-thead-helper tr:first-child').addClass('u-never--hide');
    var fillter = $('#js-filter-content');    
    $('#u-gategries--button').on('click', function(event){
        event.preventDefault();
        $(this).children('i').toggleClass('u-display--none');
        fillter.toggleClass('animated fadeIn u-display--none');
    });
});

function clearTheCompareIterms() {
    $('.delete-me').moveAndChange('tr', '#js-tbody-helper', 'style', '.material-icons', 'add');
    $('.delete-me').removeClass('delete-me');
}

function scrollEvents() {
    var $table = $('#tableSorting');
        $('.u-content--scrollable').scroll(function (event) {            
        if ($(this).scrollTop() > 200 && $(window).width() > 980) {
            $table.floatThead({
                position: 'fixed'
            });
            $('.floatThead-container').addClass('u--saviour---scroll u-scroll--trigger') + $('.floatThead-table').removeClass('u--boss');
        }
        else if ($(this).scrollTop() < 100) {
            $table.floatThead('destroy');
            $('.floatThead-container').removeClass('u-scroll--trigger');
        } else {
        }
    });
    $('.u--boss').scroll(function(event) {
       if($(window).width() < 981){
            $('#js-thead-helper').css('visibility', 'hidden');
            $('.u-never--hide').css({'margin-left': $(this).scrollLeft() + 'px'});
       }
    });
}

function fillterContent() {
    //add special classes for filter
    var manArray = [];
    var womanArray = [];
    var mdogArray = [];
    var wdogArray = [];
    var indexify = $('#category').index() + 1;
    var sliceMe = $('#js-tbody-helper tr td:nth-child(' + indexify + ')');
        $(sliceMe).each(function (index, el) {
        if ($(this).text() == "Muži" || $(this).text() == "Man") {
            $(this).parent().attr('class', 'man');
        }
        else if ($(this).text() == "Ženy" || $(this).text() == "Woman") {
            $(this).parent().attr('class', 'woman');
        }
        else if ($(this).text() == "Dogtrekking muži" || $(this).text() == "Man with dog") {
            $(this).parent().attr('class', 'mdog');

        }
        else if ($(this).text() == "Dogtrekking ženy" || $(this).text() == "Woman with dog") {
            $(this).parent().attr('class', 'wdog');
        }
    });
    
    //check if checked and filtring
    $('input').click(function (event) {
        var getID = 0;
        var lengthID = 0;
        var parentID = $('.u-tbody--helper').parents('#tableSorting'); 
        var $table = $('#tableSorting');
        $('.mdl-radio__button').not(this).removeClass('u-checkbox--active');
        $('.compare-item').removeAttr('disabled');
        $('tr').show();
        getID = $(this).attr('id');
        lengthID = $('.' + getID).length;
        if ($(this).is('#' + getID) && getID !== 'mix') {
            $table.floatThead('destroy');
            clearTheCompareItermsMobile();
            if(lengthID > 9 && lengthID < 20 && $(window).width() > 981){
                parentID.css('min-height', '1000px' );
            } else{
                parentID.css('min-height', 'auto' );
            }
            $('.u-content--scrollable').scrollTop(0);
            clearTheCompareIterms();
            $('#' + getID).toggleClass('u-checkbox--active');
            if ($(this).hasClass('u-checkbox--active')) {
                $('tr').not('.' + getID + ', .u-never--hide').hide();
            }
            else {
                $('tr').not('.' + getID).show();
            }
        }
        else {
            $('.compare-item').removeAttr('disabled');
            clearTheCompareItermsMobile();
            clearTheCompareIterms();
            $('tr').show();
        }
    });
}

function compareItems() {
    var uTbodyMargin = $('#tableSorting tbody');
    var staticMargin;
    var thisWidth;
    var countedMargin;
        $('body').on('click', '.compare-item', function (event) {
        if($(window).width() > 980){
            event.preventDefault();
            if ($(this).hasClass('delete-me')) {
                $('.compare-item').not($(this)).not('.delete-me').removeAttr('disabled');
                $(this).moveAndChange('tr', '#js-page-content .u-tbody--helper', 'style', '.material-icons', 'add');
                $(this).parents('tr').removeClass('u--orange');
            }
            else {
                if ($('.u-scroll--trigger').length !== 0) {
                    if ($('.delete-me').length < 3 && $('.delete-me').length !== 2) {
                            console.log($('.delete-me').length);
                            $(this).moveAndChange('tr', '.floatThead-table thead', '', '.material-icons', 'delete');
                            $('.floatThead-table thead tr').not('.floatThead-table thead tr:first-child').addClass('u--orange');
                    }else{
                         console.log('i am in');
                        $(this).moveAndChange('tr', '.floatThead-table thead', '', '.material-icons', 'delete');
                        $('.floatThead-table thead tr').not('.floatThead-table thead tr:first-child').addClass('u--orange');
                        $('.compare-item').not($(this)).not('.delete-me').attr('disabled', 'disabled');
                            
                    }
                }else {
                    if ($('.delete-me').length < 3 && $('.delete-me').length !== 2) {
                        $(this).moveAndChange('tr', '.u--boss thead', '', '.material-icons', 'delete');
                        $('.u--boss thead tr').not('.u--boss thead tr:first-child').addClass('u--orange');
                    }else{
                     $('.compare-item').not($(this)).not('.delete-me').attr('disabled', 'disabled');
                     $(this).moveAndChange('tr', '.u--boss thead', '', '.material-icons', 'delete');
                     $('.u--boss thead tr').not('.u--boss thead tr:first-child').addClass('u--orange');
                }
                }
            }   
        }else{
            event.preventDefault();
            if ($(this).hasClass('delete-me')) {
                $('.compare-item').not($(this)).not('.delete-me').removeAttr('disabled');
                $(this).moveAndChange('tr', '#js-page-content .u-tbody--helper', 'style', '.material-icons', 'add');
                $(this).parents('tr').removeClass('u--orange');
                staticMargin = uTbodyMargin.css('margin-left');
                thisWidth = $(this).parents('tr').width();
                countedMargin = parseInt(staticMargin) - thisWidth;
                uTbodyMargin.css('margin-left', countedMargin + 'px');
            }
            else if ($('.delete-me').length < 1) {
                $(this).moveAndChange('tr', '.u--boss thead', '', '.material-icons', 'delete');
                $('.u--boss thead tr').not('.u--boss thead tr:first-child').addClass('u--orange');
                staticMargin = uTbodyMargin.css('margin-left');
                thisWidth = $(this).parents('tr').width();
                countedMargin = parseInt(staticMargin) + thisWidth;
                uTbodyMargin.css('margin-left', countedMargin + 'px');

            }
            else{
                staticMargin = uTbodyMargin.css('margin-left');
                thisWidth = $(this).parents('tr').width();
                countedMargin = parseInt(staticMargin) + thisWidth;
                uTbodyMargin.css('margin-left', countedMargin + 'px');
                $('.compare-item').not($(this)).not('.delete-me').attr('disabled', 'disabled');
                $(this).moveAndChange('tr', '.u--boss thead', '', '.material-icons', 'delete');
                $('.u--boss thead tr').not('.u--boss thead tr:first-child').addClass('u--orange');
            }
        }
            $(this).toggleClass('delete-me');
        });
    }
function clearTheCompareItermsMobile(){
    $('#tableSorting tbody').css('margin-left', '92px');
    $('tr').removeClass('u--orange');
}

jQuery.fn.moveAndChange = function (parent_name, where_put_it, attr_name, children_name, content) {
       this.parents(parent_name).appendTo(where_put_it).removeAttr(attr_name) + this.children(children_name).text(content);
}

