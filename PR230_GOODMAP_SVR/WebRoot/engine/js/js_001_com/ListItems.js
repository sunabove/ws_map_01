var generic = {};
generic.list = function() {
    this.items = new Array();
};
generic.list.prototype.count = function() {
    return this.items.length;
};
generic.list.prototype.getItems = function() {
    return this.items;
};
generic.list.prototype.getItem = function(index) {
    return this.items[index];
};
generic.list.prototype.setItem = function(item, index) {
    this.items[index] = item;
};
generic.list.prototype.extendProperty = function(index, obj) {
    for (key in obj) {
        this.setProperty(index, key, obj[key]);
    };
};
generic.list.prototype.getProperty = function(index, property) {
    return this.items[index][property];
};
generic.list.prototype.setProperty = function(index, property, value) {
    this.items[index][property] = value;
};
generic.list.prototype.add = function(item) {
    if (this.items.length != 0) {
        var oldlen = this.items.length;
        var tmp = new Array(oldlen + 1);
        var i = 0;
        for (i = 0; i < this.items.length; i++) {
            tmp[i] = this.items[i];
        };
        tmp[(tmp.length - 1)] = item;
        this.items = new Array(tmp.length);
        for (i = 0; i < tmp.length; i++) {
            this.items[i] = tmp[i];
        };
        tmp = null;
    } else {
        this.items = new Array(1);
        this.items[0] = item;
    };
};
generic.list.prototype.insert = function(item, index) {
    if (this.items.length != 0) {
        var oldlen = this.items.length;
        var tmp = new Array(oldlen + 1);
        var i = 0;
        var j = 0;
        for (i = 0; i < tmp.length; i++) {
            if (i == index) {
                tmp[i] = item;
            } else {
                tmp[i] = this.items[j];
                j++;
            };
        };
        this.items = new Array(tmp.length);
        for (i = 0; i < tmp.length; i++) {
            this.items[i] = tmp[i];
        };
        tmp = null;
    } else {
        this.items = new Array(1);
        this.items[0] = item;
    };
};
generic.list.prototype.addRange = function(objectArray) {
    if (this.items.length != 0) {
        var oldlen = this.items.length;
        var tmp = new Array(oldlen + objectArray.length);
        var i = 0;
        for (i = 0; i < this.items.length; i++) {
            tmp[i] = this.items[i];
        };
        for (i = 0; i < objectArray.length; i++) {
            tmp[(i + oldlen)] = objectArray[i];
        };
        this.items = new Array(tmp.length);
        for (i = 0; i < tmp.length; i++) {
            this.items[i] = tmp[i];
        };
        tmp = null;
    } else {
        var iloop = 0;
        this.items = new Array(objectArray.length);
        for (iloop = 0; iloop < objectArray.length; iloop++) {
            this.items[iloop] = objectArray[iloop];
        };
    };
};
generic.list.prototype.find = function(item) {
    var index = -1;
    var i = 0;
    for (i = 0; i < this.items.length; i++) {
        if (this.compare(this.items[i], item)) {
            index = i;
            break;
        };
    };
    return index;
};
generic.list.prototype.compare = function(a, b) {
    return (a == b);
};
generic.list.prototype.remove = function(item) {
    var index = this.find(item);
    if (index != -1) {
        var tmp = new Array((this.items.length - 1));
        var i = 0;
        var j = 0;
        for (i = 0; i < this.items.length; i++) {
            if (i != index) {
                tmp[j] = this.items[i];
                j++;
            };
        };
        this.items = new Array(tmp.length);
        for (i = 0; i < tmp.length; i++) {
            this.items[i] = tmp[i];
        };
        tmp = null;
        this.count--;
    };
};
generic.list.prototype.removeAt = function(index) {
    if (this.items.length == 1) {
        this.items = null;
        this.items = new Array();
    } else {
        var tmp = new Array((this.items.length - 1));
        var i = 0;
        var j = 0;
        for (i = 0; i < this.items.length; i++) {
            if (i != index) {
                tmp[j] = this.items[i];
                j++;
            };
        };
        this.items = new Array(tmp.length);
        for (i = 0; i < tmp.length; i++) {
            this.items[i] = tmp[i];
        };
        tmp = null;
    };
};
generic.list.prototype.join = function(seprator, property) {
    var i = 0;
    var result = "";
    for (i = 0; i < this.items.length; i++) {
        if (i == (this.items.length - 1)) {
            result += (property) ? this.items[i][property] : this.items[i];
        } else {
            result += (property) ? this.items[i][property] : this.items[i];
            result += seprator;
        };
    };
    return result;
};
generic.list.prototype.clear = function() {
    this.items = new Array();
};