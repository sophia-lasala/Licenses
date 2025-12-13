    private Queue<Movie> pendingQueue = new LinkedList<>();

    // HashMap that maps license ID -> full Movie object
    private HashMap<String, Movie> licenseMap = new HashMap<>();

    // Build ArrayList (from CSV), HashMap, LinkedList and Queue
    public void buildAllStructures() {
        Data data = new Data();
        ArrayList<Movie> movieList = new ArrayList<>();

        // Fill ArrayList with movies from CSV
        Data.movieList(movieList);

        // Clear structures in case method is called more than once
        expirationList.clear();
        pendingQueue.clear();
        licenseMap.clear();

        for (Movie m : movieList) {
            // 1) HashMap: license ID -> full Movie
            licenseMap.put(m.getLicenseID(), m);

            // 2) Build expiration list with days until expiration
            long days = ChronoUnit.DAYS.between(m.getRenewalDate(), m.getExpirationDate());

            Movie temp = new Movie(
                    m.getTitle(),
                    m.getLicenseID(),
                    m.getExpirationDate(),
                    m.getRenewalDate(),
                    days
            );

            expirationList.add(temp);
        }

        // Sort linked list by days until expiration
        orgExpirationList(expirationList);

        // Build queue of licenses expiring within 30 days
        buildPendingQueue(expirationList);
    }

    // Bubble-sort the expiration list by days
    
    // Build queue of licenses that expire within 30 days
    public void buildPendingQueue(LinkedList<Movie> expirationList) {
        pendingQueue.clear();

        for (int i = 0; i < 10; i++) {
            Movie movie = expirationList.get(i);
            pendingQueue.add(movie);
        }
    }

    // Print the pending queue
    public void printPendingQueue() {
        System.out.println("Licenses expiring most recently:");
        System.out.println("Title | License ID | Expiration Date | Renewal Date | Days");
        for (Movie movie : pendingQueue) {
            System.out.println(movie.toExpirationString());
        }
    }

  
 
